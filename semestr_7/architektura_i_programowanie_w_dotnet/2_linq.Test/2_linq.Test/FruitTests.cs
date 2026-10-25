namespace _2_linq.Test;

public class FruitTests
{
    [Fact]
    public void ToString_RandomFruit_MatchesExactFormat()
    {
        UsdCourse.Current = 4.0f;
        var fruit = Fruit.Create();

        const string expected =
            @"^Fruit: Name=[A-Z][a-z]+, IsSweet=(True|False), Price=\d+,\d{2} zł, UsdPrice=\$\d+\.\d{2}$";

        Assert.Matches(expected, fruit.ToString());
    }

    [Fact]
    public void Create_CalledMultipleTimes_GeneratesMultipleDistinctNames()
    {
        var names = Enumerable
            .Range(0, 20)
            .Select(_ => Fruit.Create().Name)
            .Distinct();

        Assert.True(names.Count() > 1);
    }
}