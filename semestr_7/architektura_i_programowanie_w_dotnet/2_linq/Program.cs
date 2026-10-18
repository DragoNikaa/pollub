using _2_linq;

UsdCourse.Current = await UsdCourse.GetUsdCourseAsync();
Console.WriteLine($"Current USD course: {UsdCourse.Current}");

List<Fruit> fruits = [];
for (var i = 0; i < 15; i++)
    fruits.Add(Fruit.Create());

Console.WriteLine("\n----- FRUITS -----");
fruits.ForEach(Console.WriteLine);

var orderedSweetFruits = fruits
    .Where(fruit => fruit.IsSweet)
    .OrderByDescending(fruit => fruit.Price)
    .ToList();
var sweetFruitCount = orderedSweetFruits.Count;

Console.WriteLine("\n----- ORDERED SWEET FRUITS -----");
if (sweetFruitCount == 0)
    Console.WriteLine("All fruits are sour!");
for (var i = 0; i < sweetFruitCount; i++)
    Console.WriteLine($"{i + 1}. {orderedSweetFruits[i]}");