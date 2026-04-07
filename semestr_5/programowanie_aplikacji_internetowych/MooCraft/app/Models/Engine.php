<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Support\Str;

class Engine extends Model
{
    public static function getOrdered(): Collection
    {
        return static::orderBy('name')->get();
    }

    public static function getSnake(int $id): string
    {
        $name = static::findOrFail($id)->name;
        $snake = Str::snake($name);
        return ($snake === 'automatic' ? '' : 'magnific_') . $snake;
    }

    public function cows(): HasMany
    {
        return $this->hasMany(Cow::class)->latest();
    }
}
