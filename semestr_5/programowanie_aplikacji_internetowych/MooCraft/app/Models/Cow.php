<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Support\Facades\Storage;

class Cow extends Model
{
    protected $fillable = [
        'user_id',
        'freepik_task_id',
        'breed_id',
        'ai_model_id',
        'engine_id',
        'name',
        'creative_detailing',
        'description',
        'image_id',
    ];

    public static function updateImageId(string $freepikTaskId, string $imageId): void
    {
        static::where('freepik_task_id', $freepikTaskId)->update(['image_id' => $imageId]);
    }

    public static function deleteByFreepikTaskId(string $freepikTaskId): void
    {
        static::where('freepik_task_id', $freepikTaskId)->delete();
    }

    public static function getByFreepikTaskId(string $freepikTaskId): static
    {
        return static::firstWhere('freepik_task_id', $freepikTaskId);
    }

    public function getImagePath(): string
    {
        return Storage::url('cows/' . ($this->image_id ?? 'placeholder') . '.png');
    }

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
