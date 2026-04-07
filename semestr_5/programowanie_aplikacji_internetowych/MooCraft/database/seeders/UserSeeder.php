<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('users')->insert([
            [
                'name' => 'DragoNika',
                'email' => 'dragonika@example.com',
                'password' => Hash::make('password'),
            ],
            [
                'name' => 'TestUser1',
                'email' => 'test1@example.com',
                'password' => Hash::make('password'),
            ],
            [
                'name' => 'TestUser2',
                'email' => 'test2@example.com',
                'password' => Hash::make('password'),
            ],
        ]);
    }
}
