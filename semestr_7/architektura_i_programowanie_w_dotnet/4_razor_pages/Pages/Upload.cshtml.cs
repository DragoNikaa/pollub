using ImageMagick;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace _4_razor_pages.Pages;

public class UploadModel(IWebHostEnvironment env) : PageModel
{
    private const int MaxFileSizeInBytes = 1024 * 1024;
    private string _imagesDir = Path.Combine(env.WebRootPath, "images");
    [BindProperty] public IFormFile? Upload { get; set; }

    public IActionResult OnPost()
    {
        if (Upload == null) return RedirectToPage("Index");

        if (Upload.Length > MaxFileSizeInBytes)
        {
            ModelState.AddModelError("Upload", "File is too large (max 1 MB)");
            return Page();
        }

        using var image = new MagickImage(Upload.OpenReadStream());

        AddWatermark(image);
        Save(image, GenerateFileName(Upload.ContentType));

        return RedirectToPage("Index");
    }

    private static void AddWatermark(MagickImage image)
    {
        using var watermark = new MagickImage("watermark.png");

        watermark.Evaluate(Channels.Alpha, EvaluateOperator.Divide, 4);
        image.Composite(watermark, Gravity.Southeast, CompositeOperator.Over);
    }

    private static string GenerateFileName(string contentType)
    {
        var extension = contentType switch
        {
            "image/png" => ".png",
            "image/gif" => ".gif",
            _ => ".jpg"
        };

        return Path.GetFileNameWithoutExtension(Path.GetRandomFileName()) + extension;
    }

    private void Save(MagickImage image, string fileName)
    {
        image.Write(Path.Combine(_imagesDir, fileName));
    }
}