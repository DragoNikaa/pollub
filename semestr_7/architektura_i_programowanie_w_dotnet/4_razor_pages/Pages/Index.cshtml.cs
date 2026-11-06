using Microsoft.AspNetCore.Mvc.RazorPages;

namespace _4_razor_pages.Pages;

public class IndexModel(IWebHostEnvironment env) : PageModel
{
    private string _imagesDir = Path.Combine(env.WebRootPath, "images");
    public List<string> Images { get; set; } = [];

    public void OnGet()
    {
        UpdateFileList();
    }

    private void UpdateFileList()
    {
        Images.Clear();

        foreach (var item in Directory.EnumerateFiles(_imagesDir))
        {
            Images.Add(Path.GetFileName(item));
        }
    }
}