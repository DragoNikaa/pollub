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

        return View(await context.Movies.Include(m => m.Genre).ToListAsync());
    }

    // GET: Home/Details/5
    public async Task<IActionResult> Details(int? id)
    {
        if (id == null) return NotFound();

        var movie = await context.Movies
            .Include(m => m.Genre)
            .FirstOrDefaultAsync(m => m.Id == id);
        if (movie == null) return NotFound();

        return View(movie);
    }

    // GET: Home/Create
    public IActionResult Create()
    {
        return View(new MovieDto { AllGenres = GetAllGenres() });
    }

    // POST: Home/Create
    // To protect from overposting attacks, enable the specific properties you want to bind to.
    // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Create([Bind("Id,Title,Description,Rating,TrailerLink,Genre")] MovieDto movieDto)
    {
        if (!ModelState.IsValid)
        {
            movieDto.AllGenres = GetAllGenres();
            return View(movieDto);
        }

        context.Add(DtoToEntity(movieDto));
        await context.SaveChangesAsync();
        return RedirectToAction(nameof(Index));
    }

    // GET: Home/Edit/5
    public async Task<IActionResult> Edit(int? id)
    {
        if (id == null) return NotFound();

        var movie = await context.Movies
            .Include(m => m.Genre)
            .FirstOrDefaultAsync(m => m.Id == id);
        if (movie == null) return NotFound();

        return View(EntityToDto(movie));
    }

    // POST: Home/Edit/5
    // To protect from overposting attacks, enable the specific properties you want to bind to.
    // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Edit(int id,
        [Bind("Id,Title,Description,Rating,TrailerLink,Genre")]
        MovieDto movieDto)
    {
        if (id != movieDto.Id) return NotFound();

        if (!ModelState.IsValid)
        {
            movieDto.AllGenres = GetAllGenres();
            return View(movieDto);
        }

        try
        {
            context.Update(DtoToEntity(movieDto));
            await context.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!MovieExists(movieDto.Id)) return NotFound();

            throw;
        }

        return RedirectToAction(nameof(Index));
    }

    // GET: Home/Delete/5
    public async Task<IActionResult> Delete(int? id)
    {
        if (id == null) return NotFound();

        var movie = await context.Movies
            .Include(m => m.Genre)
            .FirstOrDefaultAsync(m => m.Id == id);
        if (movie == null) return NotFound();

        return View(movie);
    }

    // POST: Home/Delete/5
    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> DeleteConfirmed(int id)
    {
        var movie = await context.Movies.FindAsync(id);
        if (movie != null) context.Movies.Remove(movie);

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

    private Movie DtoToEntity(MovieDto dto)
    {
        var genre = context.Genres
                        .FirstOrDefault(g => g.Name == dto.Genre)
                    ?? new Genre { Name = dto.Genre };

        return new Movie
        {
            Id = dto.Id,
            Title = dto.Title,
            Description = dto.Description,
            Rating = dto.Rating,
            TrailerLink = dto.TrailerLink,
            Genre = genre
        };
    }

    private MovieDto EntityToDto(Movie entity)
    {
        return new MovieDto
        {
            Id = entity.Id,
            Title = entity.Title,
            Description = entity.Description,
            Rating = entity.Rating,
            TrailerLink = entity.TrailerLink,
            Genre = entity.Genre.Name,
            AllGenres = GetAllGenres()
        };
    }

    private List<string> GetAllGenres()
    {
        return [.. context.Genres.Select(g => g.Name)];
    }
}