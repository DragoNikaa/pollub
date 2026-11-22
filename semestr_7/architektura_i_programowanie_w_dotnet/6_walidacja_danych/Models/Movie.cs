using System.ComponentModel.DataAnnotations;

namespace _5_orm.Models;

public class Movie
{
    [Key] public int Id { get; set; }

    [Required(ErrorMessage = "Title is required")]
    [MaxLength(50, ErrorMessage = "Title must be between 1 and 50 characters")]
    public required string Title { get; set; }

    [UIHint("LongText")]
    [Required(ErrorMessage = "Description is required")]
    public required string Description { get; set; }

    [UIHint("Stars")]
    [Required(ErrorMessage = "Rating is required")]
    [Range(1, 5, ErrorMessage = "Rating must be a number between 1 and 5")]
    public required int Rating { get; set; }

    public string? TrailerLink { get; set; }

    [Required(ErrorMessage = "Genre is required")]
    public required Genre Genre { get; set; }
}