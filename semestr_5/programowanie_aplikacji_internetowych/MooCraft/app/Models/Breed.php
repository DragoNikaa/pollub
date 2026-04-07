<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Breed extends Model
{
    public static function getOrdered(): Collection
    {
        return static::orderBy('name')->get();
    }

    public static function getName(int $id): string
    {
        return static::findOrFail($id)->name;
    }

    public function cows(): HasMany
    {
        return $this->hasMany(Cow::class)->latest();
    }
}
