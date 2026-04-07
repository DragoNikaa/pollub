<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Support\Str;

class AiModel extends Model
{
    public static function getOrdered(): Collection
    {
        return static::orderBy('name')->get();
    }

    public static function getSnake(int $id): string
    {
        $name = static::findOrFail($id)->name;
        return Str::snake($name);
    }

    public function cows(): HasMany
    {
        return $this->hasMany(Cow::class)->latest();
    }
}
