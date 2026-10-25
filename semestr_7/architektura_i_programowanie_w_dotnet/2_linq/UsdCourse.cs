using System.Globalization;
using System.Xml.Linq;

namespace _2_linq;

public static class UsdCourse
{
    public static float Current = 0;

    private static readonly HttpClient Client = new();

    public static async Task<float> GetUsdCourseAsync()
    {
        var response = await Client.GetAsync("https://api.nbp.pl/api/exchangerates/tables/a/?format=xml");

        if (!response.IsSuccessStatusCode)
            throw new InvalidOperationException();

        var document = XDocument.Parse(await response.Content.ReadAsStringAsync());

        var midUsdValue = document.Descendants("Rate")
            .Where(rate => (string?)rate.Element("Code") == "USD")
            .Select(rate => (string?)rate.Element("Mid"))
            .FirstOrDefault();

        return midUsdValue is null
            ? throw new InvalidOperationException()
            : Convert.ToSingle(midUsdValue, CultureInfo.InvariantCulture);
    }
}