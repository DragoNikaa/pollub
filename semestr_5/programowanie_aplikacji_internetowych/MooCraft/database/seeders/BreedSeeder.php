<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class BreedSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('breeds')->insert([
            ['name' => 'Angus'],
            ['name' => 'Ayrshire'],
            ['name' => 'Brahman'],
            ['name' => 'Brown Swiss'],
            ['name' => 'Charolais'],
            ['name' => 'Dexter'],
            ['name' => 'Guernsey'],
            ['name' => 'Hereford'],
            ['name' => 'Highland'],
            ['name' => 'Holstein'],
            ['name' => 'Jersey'],
            ['name' => 'Texas Longhorn'],
        ]);
    }
}
