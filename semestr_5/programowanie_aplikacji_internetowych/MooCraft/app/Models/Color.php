<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Color extends Model
{
    public function cow(): BelongsTo
    {
        return $this->belongsTo(Cow::class);
    }
}
