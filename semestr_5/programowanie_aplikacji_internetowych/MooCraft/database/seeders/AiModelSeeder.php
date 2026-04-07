<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class AiModelSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('ai_models')->insert([
            [
                'name' => 'Realism',
                'description' => 'With a more realistic color palette. It tries to give an extra boost of reality to your images, a kind of "less AI look". Works especially well with photographs but also magically works with illustrations too. IMPORTANT: you should use Zen, Flexible or Fluid if you are trying to generate something that is really fantastic, Realism may not follow your prompt well.',
            ],
            [
                'name' => 'Zen',
                'description' => 'For smoother, basic, and cleaner results. Fewer objects in the scene and less intricate details. The softer looking one.',
            ],
            [
                'name' => 'Flexible',
                'description' => "Good prompt adherence. However, it has results that are a bit more HDR and saturated than Realism or Fluid. It's especially good with illustrations, fantastical prompts, and for diving into the latent space in search of very specific visual styles.",
            ],
            [
                'name' => 'Fluid',
                'description' => 'The model that adheres best to prompts with great average quality for all kind of images. It can generate really creative images! It will always follow your input no matter what.',
            ],
            [
                'name' => 'Super Real',
                'description' => 'If reality is your priority, this is your model. Nearly as versatile as Flexible, it excels in realism outperforming Editorial Portraits in medium shots, though not as strong for close-ups.',
            ],
            [
                'name' => 'Editorial Portraits',
                'description' => 'The most amazing state-of-the-art generator for editorial portraits. You have never seen a level of realism like this before. Perfect for hyperrealistic close-up or medium shots. Unfortunately, in wide or distant shots, it generates anatomical problems and artifacts… but for close-ups, it is simply the best on the market. Tip: use the longest and most explanatory prompts possible, they really suit it well!',
            ],
        ]);
    }
}
