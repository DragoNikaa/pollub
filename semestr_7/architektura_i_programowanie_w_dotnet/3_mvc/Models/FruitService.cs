namespace _3_mvc.Models;

public class FruitService
{
    private readonly List<Fruit> _fruits;

    public FruitService()
    {
        _fruits = [];

        for (var i = 1; i <= 10; i++)
        {
            var fruit = Fruit.Create();
            fruit.Id = i;
            _fruits.Add(fruit);
        }
    }

    public void Add(Fruit fruit)
    {
        fruit.Id = _fruits
            .Select(item => item.Id)
            .DefaultIfEmpty(0)
            .Max() + 1;

        _fruits.Add(fruit);
    }

    public bool Remove(int id)
    {
        var toRemove = _fruits.FirstOrDefault(item => item.Id == id);
        return toRemove != null && _fruits.Remove(toRemove);
    }

    public IEnumerable<Fruit> GetFruits(string? sortKey)
    {
        return sortKey switch
        {
            FruitSortKey.Name => _fruits.OrderBy(item => item.Name),
            FruitSortKey.IsSweet => _fruits.OrderBy(item => item.IsSweet),
            FruitSortKey.Price => _fruits.OrderBy(item => item.Price),
            _ => _fruits
        };
    }
}