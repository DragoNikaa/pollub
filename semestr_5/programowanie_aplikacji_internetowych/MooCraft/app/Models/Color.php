<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Color extends Model
{
    protected $fillable = [
        'cow_id',
        'hex_code',
        'weight',
    ];

    public $timestamps = false;

    public function cow(): BelongsTo
    {
        return $this->belongsTo(Cow::class);
    }
}
