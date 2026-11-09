using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using _5_orm.Data;
using _5_orm.Models;

namespace _5_orm.Controllers;

public class HomeController(MoviesDbContext context) : Controller
{
    // GET: Home
    public async Task<IActionResult> Index()
    {
        ViewBag.TopMovieId = await GetTopMovieIdAsync();

        return View(await context.Movies.ToListAsync());
    }

    // GET: Home/Details/5
    public async Task<IActionResult> Details(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var movie = await context.Movies
            .FirstOrDefaultAsync(m => m.Id == id);
        if (movie == null)
        {
            return NotFound();
        }

        return View(movie);
    }

    // GET: Home/Create
    public IActionResult Create()
    {
        return View();
    }

    // POST: Home/Create
    // To protect from overposting attacks, enable the specific properties you want to bind to.
    // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Create([Bind("Id,Title,Description,Rating,TrailerLink")] Movie movie)
    {
        if (!ModelState.IsValid) return View(movie);

        context.Add(movie);
        await context.SaveChangesAsync();
        return RedirectToAction(nameof(Index));
    }

    // GET: Home/Edit/5
    public async Task<IActionResult> Edit(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var movie = await context.Movies.FindAsync(id);
        if (movie == null)
        {
            return NotFound();
        }

        return View(movie);
    }

    // POST: Home/Edit/5
    // To protect from overposting attacks, enable the specific properties you want to bind to.
    // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Edit(int id, [Bind("Id,Title,Description,Rating,TrailerLink")] Movie movie)
    {
        if (id != movie.Id)
        {
            return NotFound();
        }

        if (!ModelState.IsValid) return View(movie);

        try
        {
            context.Update(movie);
            await context.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!MovieExists(movie.Id))
            {
                return NotFound();
            }

            throw;
        }

        return RedirectToAction(nameof(Index));
    }

    // GET: Home/Delete/5
    public async Task<IActionResult> Delete(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var movie = await context.Movies
            .FirstOrDefaultAsync(m => m.Id == id);
        if (movie == null)
        {
            return NotFound();
        }

        return View(movie);
    }

    // POST: Home/Delete/5
    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> DeleteConfirmed(int id)
    {
        var movie = await context.Movies.FindAsync(id);
        if (movie != null)
        {
            context.Movies.Remove(movie);
        }

        await context.SaveChangesAsync();
        return RedirectToAction(nameof(Index));
    }

    private bool MovieExists(int id)
    {
        return context.Movies.Any(e => e.Id == id);
    }

    private async Task<int?> GetTopMovieIdAsync()
    {
        var topMovie = await context.Movies
            .OrderByDescending(m => m.Rating)
            .FirstOrDefaultAsync();
        return topMovie?.Id;
    }
}