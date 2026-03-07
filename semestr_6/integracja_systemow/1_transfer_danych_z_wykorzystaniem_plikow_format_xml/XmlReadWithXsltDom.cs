using System.Xml;
using System.Xml.XPath;

namespace _1_transfer_danych_z_wykorzystaniem_plikow_format_xml
{
    internal class XmlReadWithXsltDom
    {
        internal static void Read(string filePath)
        {
            var navigator = CreateNavigator(filePath);
            var namespaceManager = CreateNamespaceManager(navigator);

            DisplayCount(navigator, namespaceManager, "Mometasoni furoas", "Krem");
            DisplayMultiFormCount(navigator, namespaceManager);
            DisplayTopManufacturers(navigator, namespaceManager, "Krem");
            DisplayTopManufacturers(navigator, namespaceManager, "Tabletki");
            DisplayDrugsWithActiveIngredientCount(navigator, namespaceManager, 5);

            var manufacturer = "InPharm Sp. z o.o.";
            var formStatistics = GetFormStatistics(navigator, namespaceManager, manufacturer);
            DrawHistogram(formStatistics, $"Top postacie farmaceutyczne produkowane przez {manufacturer}");
        }

        private static void DisplayCount(XPathNavigator navigator, XmlNamespaceManager namespaceManager, string activeIngredient, string form)
        {
            var query = navigator.Compile($"/x:produktyLecznicze/x:produktLeczniczy" +
                $"[@nazwaPowszechnieStosowana='{activeIngredient}' and @nazwaPostaciFarmaceutycznej='{form}']");
            query.SetContext(namespaceManager);
            var count = navigator.Select(query).Count;

            Console.WriteLine($"Liczba produktów leczniczych w postaci \"{form}\", " +
                $"których jedyną substancją czynną jest \"{activeIngredient}\": {count}");
        }

        private static void DisplayMultiFormCount(XPathNavigator navigator, XmlNamespaceManager namespaceManager)
        {
            var query = navigator.Compile("/x:produktyLecznicze/x:produktLeczniczy");
            query.SetContext(namespaceManager);
            var iterator = navigator.Select(query);

            var activeIngredientToForms = new Dictionary<string, HashSet<string>>();

            while (iterator.MoveNext())
            {
                var activeIngredient = GetAttribute(iterator, "nazwaPowszechnieStosowana");
                var form = GetAttribute(iterator, "nazwaPostaciFarmaceutycznej");

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

        private static void DisplayTopManufacturers(XPathNavigator navigator, XmlNamespaceManager namespaceManager, string form, int maxResults = 3)
        {
            var query = navigator.Compile($"/x:produktyLecznicze/x:produktLeczniczy" +
                $"[@nazwaPostaciFarmaceutycznej='{form}']");
            query.SetContext(namespaceManager);
            var iterator = navigator.Select(query);

            var manufacturerToFormCount = new Dictionary<string, int>();

            while (iterator.MoveNext())
            {
                var manufacturer = GetAttribute(iterator, "podmiotOdpowiedzialny");
                manufacturerToFormCount[manufacturer] = manufacturerToFormCount.GetValueOrDefault(manufacturer) + 1;
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

        private static void DisplayDrugsWithActiveIngredientCount(XPathNavigator navigator, XmlNamespaceManager namespaceManager, int activeIngredientCount, int maxResults = 5)
        {
            var query = navigator.Compile($"/x:produktyLecznicze/x:produktLeczniczy[" +
                $"count(x:substancjeCzynne/x:substancjaCzynna) = {activeIngredientCount}]");
            query.SetContext(namespaceManager);
            var iterator = navigator.Select(query);

            var results = 0;

            Console.WriteLine($"\nProdukty lecznicze zawierające {activeIngredientCount} substancji czynnych:");

            while (iterator.MoveNext() && results < maxResults)
            {
                Console.WriteLine($"- {GetAttribute(iterator, "nazwaProduktu")}");
                results++;
            }
        }

        private static Dictionary<string, int> GetFormStatistics(XPathNavigator navigator, XmlNamespaceManager namespaceManager, string manufacturer)
        {
            var query = navigator.Compile($"/x:produktyLecznicze/x:produktLeczniczy" +
                $"[@podmiotOdpowiedzialny='{manufacturer}']");
            query.SetContext(namespaceManager);
            var iterator = navigator.Select(query);

            var statistics = new Dictionary<string, int>();

            while (iterator.MoveNext())
            {
                var value = GetAttribute(iterator, "nazwaPostaciFarmaceutycznej")!;
                statistics[value] = statistics.GetValueOrDefault(value) + 1;
            }
            return statistics;
        }

        private static void DrawHistogram(Dictionary<string, int> statistics, string title, int maxResults = 10)
        {
            Console.WriteLine($"\n{title}:");

            var sortedStatistics = statistics
                .OrderByDescending(item => item.Value)
                .Take(maxResults);
            var maxValue = sortedStatistics.First().Value;
            var maxLabelLength = sortedStatistics.Max(item => item.Key.Length);
            var maxBarWidth = 50;

            foreach (var (index, item) in sortedStatistics.Index())
            {
                var barWidth = (int)((double)item.Value / maxValue * maxBarWidth);
                var bar = new string('█', barWidth);

                Console.WriteLine($"{index + 1,2}. {item.Key.PadRight(maxLabelLength)} [{item.Value,4}] {bar}");
            }
        }

        private static XPathNavigator CreateNavigator(string filePath)
        {
            var document = new XPathDocument(filePath);
            return document.CreateNavigator();
        }

        private static XmlNamespaceManager CreateNamespaceManager(XPathNavigator navigator)
        {
            var manager = new XmlNamespaceManager(navigator.NameTable);
            manager.AddNamespace("x", "http://rejestry.ezdrowie.gov.pl/rpl/eksport-danych-v6.0.0");
            return manager;
        }

        private static string GetAttribute(XPathNodeIterator iterator, string attribute)
        {
            return iterator.Current!.GetAttribute(attribute, "");
        }
    }
}
