using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace _4_razor_pages.Pages;

public class SingleModel(IWebHostEnvironment env) : PageModel
{
    private string _imagesDir = Path.Combine(env.WebRootPath, "images");
    [BindProperty(SupportsGet = true)] public string? Image { get; set; }

    public IActionResult OnGet()
    {
        if (!string.IsNullOrEmpty(Image) && System.IO.File.Exists(Path.Combine(_imagesDir, Image)))
        {
            return Page();
        }

        return NotFound();
    }
}