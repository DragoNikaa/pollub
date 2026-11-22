using System.ComponentModel.DataAnnotations;

namespace _5_orm.Models;

public class MovieDto
{
    public int Id { get; set; }

    [Required(ErrorMessage = "Title is required")]
    [MaxLength(50, ErrorMessage = "Title must be between 1 and 50 characters")]
    public string Title { get; set; } = "";

    [UIHint("LongText")]
    [Required(ErrorMessage = "Description is required")]
    public string Description { get; set; } = "";

    [UIHint("Stars")]
    [Required(ErrorMessage = "Rating is required")]
    [Range(1, 5, ErrorMessage = "Rating must be a number between 1 and 5")]
    public int Rating { get; set; } = 5;

    public string? TrailerLink { get; set; }

    [Required(ErrorMessage = "Genre is required")]
    public string Genre { get; set; } = "";

    public List<string>? AllGenres { get; set; }
}