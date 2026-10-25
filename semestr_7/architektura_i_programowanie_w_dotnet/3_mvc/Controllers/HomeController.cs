using System.Diagnostics;
using _3_mvc.Models;
using Microsoft.AspNetCore.Mvc;

namespace _3_mvc.Controllers;

public class HomeController(ILogger<HomeController> logger, FruitService fruitService) : Controller
{
    public IActionResult Index()
    {
        ViewData["random"] = Random.Shared.NextDouble();
        return View();
    }

    public IActionResult Fruits([FromQuery] string? sort = null)
    {
        return View(fruitService.GetFruits(sort));
    }

    public IActionResult Create()
    {
        return View();
    }

    [HttpPost]
    public IActionResult Create(Fruit fruit)
    {
        if (!ModelState.IsValid) return View();

        fruitService.Add(fruit);
        return RedirectToAction("Fruits");
    }

    public IActionResult Delete(int id)
    {
        if (fruitService.Remove(id))
            return RedirectToAction("Fruits");

        return NotFound();
    }

    public IActionResult Privacy()
    {
        return View();
    }

    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public IActionResult Error()
    {
        return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
    }
}