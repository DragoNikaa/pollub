<?php

use App\Http\Controllers\CowController;
use App\Http\Controllers\ProfileController;
use App\Models\AiModel;
use App\Models\Engine;
use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('home', [
        'aiModels' => AiModel::getOrdered(),
        'engines' => Engine::getOrdered(),
    ]);
})->name('home');

Route::get('/dashboard', function () {
    return view('dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

Route::get('/cows', [CowController::class, 'index'])->name('cows.index');

Route::middleware('auth')->group(function () {
    Route::get('/cows/create', [CowController::class, 'create'])->name('cows.create');
    Route::post('/cows', [CowController::class, 'store'])->name('cows.store');
    Route::get('/cows/{cow}/edit', [CowController::class, 'edit'])->name('cows.edit');
    Route::patch('/cows/{cow}', [CowController::class, 'update'])->name('cows.update');
    Route::delete('/cows/{cow}', [CowController::class, 'destroy'])->name('cows.destroy');

    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});

require __DIR__ . '/auth.php';
