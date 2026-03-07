using System.Xml;

namespace _1_transfer_danych_z_wykorzystaniem_plikow_format_xml
{
    internal class XmlReadWithSaxApproach
    {
        internal static void Read(string filePath)
        {
            DisplayCount(CreateReader(filePath), "Mometasoni furoas", "Krem");
            DisplayMultiFormCount(CreateReader(filePath));
            DisplayTopManufacturers(CreateReader(filePath), "Krem");
            DisplayTopManufacturers(CreateReader(filePath), "Tabletki");
            DisplayDrugsWithActiveIngredientCount(CreateReader(filePath), 5);

            var formStatistics = GetAttributeStatistics(CreateReader(filePath), "nazwaPostaciFarmaceutycznej");
            DrawHistogram(formStatistics, "Top postacie farmaceutyczne");
            var manufacturerStatistics = GetAttributeStatistics(CreateReader(filePath), "podmiotOdpowiedzialny");
            DrawHistogram(manufacturerStatistics, "Top producenci");
        }

        private static void DisplayCount(XmlReader reader, string activeIngredient, string form)
        {
            var count = 0;

            while (reader.Read())
            {
                if (!IsDrugElement(reader)) continue;

                if (reader.GetAttribute("nazwaPowszechnieStosowana") == activeIngredient
                    && reader.GetAttribute("nazwaPostaciFarmaceutycznej") == form)
                {
                    count++;
                }
            }

            Console.WriteLine($"Liczba produktów leczniczych w postaci \"{form}\", " +
                $"których jedyną substancją czynną jest \"{activeIngredient}\": {count}");
        }

        private static void DisplayMultiFormCount(XmlReader reader)
        {
            var activeIngredientToForms = new Dictionary<string, HashSet<string>>();

            while (reader.Read())
            {
                if (!IsDrugElement(reader)) continue;

                var activeIngredient = reader.GetAttribute("nazwaPowszechnieStosowana")!;
                var form = reader.GetAttribute("nazwaPostaciFarmaceutycznej")!;

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

        private static void DisplayTopManufacturers(XmlReader reader, string form, int maxResults = 3)
        {
            var manufacturerToFormCount = new Dictionary<string, int>();

            while (reader.Read())
            {
                if (!IsDrugElement(reader)) continue;

                var manufacturer = reader.GetAttribute("podmiotOdpowiedzialny")!;

                if (reader.GetAttribute("nazwaPostaciFarmaceutycznej") == form)
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

        private static void DisplayDrugsWithActiveIngredientCount(XmlReader reader, int activeIngredientCount, int maxResults = 5)
        {
            var results = 0;

            Console.WriteLine($"\nProdukty lecznicze zawierające {activeIngredientCount} substancji czynnych:");

            while (reader.Read() && results < maxResults)
            {
                if (!IsDrugElement(reader)) continue;

                var subReader = reader.ReadSubtree();
                var drugName = reader.GetAttribute("nazwaProduktu");
                var currentActiveIngredientCount = 0;

                while (subReader.Read())
                {
                    if (IsActiveIngredientElement(subReader)) currentActiveIngredientCount++;
                }

                if (currentActiveIngredientCount == activeIngredientCount)
                {
                    Console.WriteLine($"- {drugName}");
                    results++;
                }
            }
        }

        private static Dictionary<string, int> GetAttributeStatistics(XmlReader reader, string attributeName)
        {
            var statistics = new Dictionary<string, int>();

            while (reader.Read())
            {
                if (!IsDrugElement(reader)) continue;

                var value = reader.GetAttribute(attributeName)!;
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

        private static XmlReader CreateReader(string filePath)
        {
            var settings = new XmlReaderSettings()
            {
                IgnoreComments = true,
                IgnoreProcessingInstructions = true,
                IgnoreWhitespace = true
            };
            var reader = XmlReader.Create(filePath, settings);
            reader.MoveToContent();
            return reader;
        }

        private static bool IsDrugElement(XmlReader reader)
        {
            return IsElement(reader, "produktLeczniczy");
        }

        private static bool IsActiveIngredientElement(XmlReader reader)
        {
            return IsElement(reader, "substancjaCzynna");
        }

        private static bool IsElement(XmlReader reader, string elementName)
        {
            return reader.NodeType == XmlNodeType.Element && reader.Name == elementName;
        }
    }
}
