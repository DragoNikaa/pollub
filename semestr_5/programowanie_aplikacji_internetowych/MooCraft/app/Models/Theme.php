<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Theme extends Model
{
    public static function getOrdered(): Collection
    {
        return static::orderBy('name')->get();
    }

    public static function getNames(array $ids): string
    {
        return static::findOrFail($ids)->pluck('name')->implode(', ');
    }

    public function cows(): BelongsToMany
    {
        return $this->belongsToMany(Cow::class)->latest();
    }
}
