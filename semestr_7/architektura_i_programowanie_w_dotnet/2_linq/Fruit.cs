namespace _2_linq;

public class Fruit
{
    public string Name { get; set; } = "";
    public bool IsSweet { get; set; }
    public double Price { get; set; }

    public double UsdPrice => Price / UsdCourse.Current;

    public static Fruit Create()
    {
        var random = Random.Shared;
        string[] names =
        [
            "Apple",
            "Banana",
            "Cherry",
            "Durian",
            "Elderberry",
            "Grape",
            "Jackfruit"
        ];

        return new Fruit
        {
            Name = names[random.Next(names.Length)],
            IsSweet = random.NextDouble() > 0.5,
            Price = random.NextDouble() * 10
        };
    }

    public override string ToString()
    {
        return $"Fruit: Name={Name}, IsSweet={IsSweet}, Price={Price:C}, " +
               $"UsdPrice={CurrencyFormatter.FormatUsdPrice(UsdPrice)}";
    }
}