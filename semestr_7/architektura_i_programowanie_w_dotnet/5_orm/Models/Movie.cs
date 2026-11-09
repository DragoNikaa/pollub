using System.ComponentModel.DataAnnotations;

namespace _5_orm.Models;

public class Movie
{
    public int Id { get; set; }

    [StringLength(100, MinimumLength = 2)] public required string Title { get; set; }

    [UIHint("LongText")]
    [StringLength(1000, MinimumLength = 2)]
    public required string Description { get; set; }

    [UIHint("Stars")] [Range(1, 5)] public int Rating { get; set; }

    [Url] [StringLength(512)] public required string TrailerLink { get; set; }
}