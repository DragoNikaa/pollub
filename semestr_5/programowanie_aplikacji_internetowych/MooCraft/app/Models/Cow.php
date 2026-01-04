<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Cow extends Model
{
    protected $fillable = [
        'user_id',
        'breed_id',
        'ai_model_id',
        'engine_id',
        'name',
        'creative_detailing',
        'description',
        'image_path',
    ];

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

    public function breed(): BelongsTo
    {
        return $this->belongsTo(Breed::class);
    }

    public function aiModel(): BelongsTo
    {
        return $this->belongsTo(AiModel::class);
    }

    public function engine(): BelongsTo
    {
        return $this->belongsTo(Engine::class);
    }

    public function themes(): BelongsToMany
    {
        return $this->belongsToMany(Theme::class)->orderBy('name');
    }

    public function colors(): HasMany
    {
        return $this->hasMany(Color::class)->orderBy('weight', 'desc');
    }
}
