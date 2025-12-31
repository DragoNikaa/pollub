<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class AiModel extends Model
{
    public function cows(): HasMany
    {
        return $this->hasMany(Cow::class)->latest();
    }
}
