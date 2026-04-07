<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class ThemeSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('themes')->insert([
            ['name' => 'Abstract'],
            ['name' => 'Art'],
            ['name' => 'Chaos'],
            ['name' => 'Disco'],
            ['name' => 'Fantasy'],
            ['name' => 'Magic'],
            ['name' => 'Meme'],
            ['name' => 'Mythical'],
            ['name' => 'Nature'],
            ['name' => 'Pixel'],
            ['name' => 'Rainbow'],
            ['name' => 'Robot'],
            ['name' => 'Sci-fi'],
            ['name' => 'Space'],
            ['name' => 'Student'],
            ['name' => 'Vintage'],
        ]);
    }
}
