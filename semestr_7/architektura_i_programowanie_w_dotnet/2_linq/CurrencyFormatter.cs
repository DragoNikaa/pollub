using System.Globalization;

namespace _2_linq;

public static class CurrencyFormatter
{
    public static string FormatUsdPrice(double price)
    {
        return price.ToString("C", new CultureInfo("en-US"));
    }
}