using _5_orm.Models;
using Microsoft.EntityFrameworkCore;

namespace _5_orm.Data;

public class MoviesDbContext(DbContextOptions options) : DbContext(options)
{
    public DbSet<Movie> Movies { get; set; }
}