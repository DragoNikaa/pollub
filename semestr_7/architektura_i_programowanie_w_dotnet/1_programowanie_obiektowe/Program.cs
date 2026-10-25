using System.Text.Json;
using _1_programowanie_obiektowe;

var drawn = new Random().Next(1, 101);
int guess;
var trials = 0;

Console.WriteLine("--- Guess a number between 1 and 100 --- ");
do
{
    Console.Write("Enter a number: ");
    while (!int.TryParse(Console.ReadLine(), out guess))
        Console.Write("That is not a valid number!\nEnter a number: ");

    trials++;

    if (guess < drawn) Console.WriteLine("Too low!");
    else if (guess > drawn) Console.WriteLine("Too high!");
} while (guess != drawn);

const string fileName = "high_scores.txt";
var highScores = File.Exists(fileName)
    ? JsonSerializer.Deserialize<List<HighScore>>(File.ReadAllText(fileName)) ?? []
    : [];

Console.Write($"\nYou won in {trials} trials!\nEnter your name: ");
highScores.Add(new HighScore { Name = Console.ReadLine() ?? "Unknown", Trials = trials });
File.WriteAllText(fileName, JsonSerializer.Serialize(highScores));

highScores.Sort((a, b) => a.Trials - b.Trials);
Console.WriteLine("\n--- High scores --- ");
foreach (var highScore in highScores)
    Console.WriteLine($"{highScore.Name} - {highScore.Trials} trials");