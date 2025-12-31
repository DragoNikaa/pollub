<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Theme extends Model
{
    public function cows(): BelongsToMany
    {
        return $this->belongsToMany(Cow::class)->latest();
    }
}
