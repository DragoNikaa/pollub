using System.Xml;

namespace _1_transfer_danych_z_wykorzystaniem_plikow_format_xml
{
    internal class XmlReadWithDomApproach
    {
        internal static void Read(string filePath)
        {
            var document = LoadDocument(filePath);
            var namespaceManager = CreateNamespaceManager(document);
            var drugs = document.GetElementsByTagName("produktLeczniczy");

            DisplayCount(drugs, "Mometasoni furoas", "Krem");
            DisplayMultiFormCount(drugs);
            DisplayTopManufacturers(drugs, "Krem");
            DisplayTopManufacturers(drugs, "Tabletki");
            DisplayDrugsWithActiveIngredientCount(namespaceManager, drugs, 5);
        }

        private static void DisplayCount(XmlNodeList drugs, string activeIngredient, string form)
        {
            var count = 0;

            foreach (XmlNode drug in drugs)
            {
                if (GetAttribute(drug, "nazwaPowszechnieStosowana") == activeIngredient
                    && GetAttribute(drug, "nazwaPostaciFarmaceutycznej") == form)
                {
                    count++;
                }
            }

            Console.WriteLine($"Liczba produktów leczniczych w postaci \"{form}\", " +
                $"których jedyną substancją czynną jest \"{activeIngredient}\": {count}");
        }

        private static void DisplayMultiFormCount(XmlNodeList drugs)
        {
            var activeIngredientToForms = new Dictionary<string, HashSet<string>>();

            foreach (XmlNode drug in drugs)
            {
                var activeIngredient = GetAttribute(drug, "nazwaPowszechnieStosowana");
                var form = GetAttribute(drug, "nazwaPostaciFarmaceutycznej");

                activeIngredientToForms.TryAdd(activeIngredient, []);
                activeIngredientToForms[activeIngredient].Add(form);
            }

            var count = 0;

            foreach (var forms in activeIngredientToForms.Values)
            {
                if (forms.Count > 1) count++;
            }

            Console.WriteLine($"Liczba produktów leczniczych o takiej samej nazwie powszechnej, " +
                $"pod różnymi postaciami: {count}");
        }

        private static void DisplayTopManufacturers(XmlNodeList drugs, string form, int maxResults = 3)
        {
            var manufacturerToFormCount = new Dictionary<string, int>();

            foreach (XmlNode drug in drugs)
            {
                var manufacturer = GetAttribute(drug, "podmiotOdpowiedzialny");

                if (GetAttribute(drug, "nazwaPostaciFarmaceutycznej") == form)
                {
                    manufacturerToFormCount[manufacturer] = manufacturerToFormCount.GetValueOrDefault(manufacturer) + 1;
                }
            }

            var topManufacturers = manufacturerToFormCount
                .OrderByDescending(item => item.Value)
                .Take(maxResults);

            Console.WriteLine($"\nPodmioty produkujące najwięcej \"{form}\":");

            foreach (var (index, item) in topManufacturers.Index())
            {
                Console.WriteLine($"{index + 1}. {item.Key}: {item.Value} produktów");
            }
        }

        private static void DisplayDrugsWithActiveIngredientCount(XmlNamespaceManager namespaceManager, XmlNodeList drugs, int activeIngredientCount, int maxResults = 5)
        {
            var results = 0;

            Console.WriteLine($"\nProdukty lecznicze zawierające {activeIngredientCount} substancji czynnych:");

            foreach (XmlNode drug in drugs)
            {
                if (drug.SelectNodes("x:substancjeCzynne/x:substancjaCzynna", namespaceManager)?.Count == activeIngredientCount)
                {
                    Console.WriteLine($"- {GetAttribute(drug, "nazwaProduktu")}");

                    results++;
                    if (results >= maxResults) return;
                }
            }
        }

        private static XmlDocument LoadDocument(string filePath)
        {
            var document = new XmlDocument();
            document.Load(filePath);
            return document;
        }

        private static XmlNamespaceManager CreateNamespaceManager(XmlDocument document)
        {
            var manager = new XmlNamespaceManager(document.NameTable);
            manager.AddNamespace("x", "http://rejestry.ezdrowie.gov.pl/rpl/eksport-danych-v6.0.0");
            return manager;
        }

        private static string GetAttribute(XmlNode drug, string attribute)
        {
            return drug.Attributes!.GetNamedItem(attribute)!.Value!;
        }
    }
}
