using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace _3_mvc.Models;

public class Fruit
{
    public int Id { get; set; }

    [DisplayName("Nazwa")] public required string Name { get; init; }

    [DisplayName("Czy słodki")] public bool IsSweet { get; init; }


    [DisplayName("Cena"), DisplayFormat(DataFormatString = "{0:C}")]
    public double Price { get; init; }

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
}