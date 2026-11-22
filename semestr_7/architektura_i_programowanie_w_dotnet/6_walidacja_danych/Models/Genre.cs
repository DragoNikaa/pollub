using System.ComponentModel.DataAnnotations;

namespace _5_orm.Models;

public class Genre
{
    [Key] public int Id { get; set; }

    [Required(ErrorMessage = "Name is required")]
    public required string Name { get; set; }
}