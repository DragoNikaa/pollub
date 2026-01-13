<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class EngineSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('engines')->insert([
            [
                'name' => 'Automatic',
                'description' => 'Default choice.',
            ],
            [
                'name' => 'Illusio',
                'description' => 'For smoother illustrations, landscapes, and nature. The softer looking one.',
            ],
            [
                'name' => 'Sharpy',
                'description' => 'Better for realistic images like photographs and for a more grainy look. It provides the sharpest and most detailed images. If you use it for illustrations it will give them more texture and a less softer look.',
            ],
            [
                'name' => 'Sparkle',
                'description' => "Also good for realistic images. It's a middle ground between Illusio and Sharpy.",
            ],
        ]);
    }
}
