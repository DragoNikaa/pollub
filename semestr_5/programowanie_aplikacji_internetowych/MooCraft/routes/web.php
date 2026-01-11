<?php

use App\Http\Controllers\CowController;
use App\Http\Controllers\ProfileController;
use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('home');
})->name('home');

Route::get('/dashboard', function () {
    return view('dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

Route::get('/cows', [CowController::class, 'index'])->name('cows.index');

Route::middleware('auth')->group(function () {
    Route::get('/cows/create', [CowController::class, 'create'])->name('cows.create');
    Route::post('/cows', [CowController::class, 'store'])->name('cows.store');

    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});

require __DIR__ . '/auth.php';
