<?php

namespace App\Http\Controllers;

use App\Models\AiModel;
use App\Models\Breed;
use App\Models\Engine;
use App\Models\Theme;
use App\Services\FreepikAiService;
use Illuminate\Http\Request;

class CowController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        return view('cows.create', [
            'breeds' => Breed::getOrdered(),
            'themes' => Theme::getOrdered(),
            'aiModels' => AiModel::getOrdered(),
            'engines' => Engine::getOrdered(),
        ]);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $validated = $this->validate($request);
        $validated['freepik_task_id'] = $this->startImageGeneration($validated);
        $this->save($validated);
        return redirect(route('home'));
    }

    private function validate(Request $request)
    {
        return $request->validate([
            'name' => ['required', 'string', 'between:3,255'],

            'breed_id' => ['required', 'integer', 'exists:breeds,id'],

            'theme_ids' => ['nullable', 'array', 'min:1'],
            'theme_ids.*' => ['distinct', 'integer', 'exists:themes,id'],

            'colors' => ['nullable', 'array', 'between:1,5'],
            'colors.*.color' => ['required', 'distinct', 'string', 'hex_color'],
            'colors.*.weight' => ['required', 'numeric', 'between:0.05,1'],

            'creative_detailing' => ['required', 'integer', 'between:0,100'],

            'ai_model_id' => ['required', 'integer', 'exists:ai_models,id'],
            'engine_id' => ['required', 'integer', 'exists:engines,id'],

            'description' => ['nullable', 'string', 'min:3'],
        ]);
    }

    private function startImageGeneration(array $validated): string
    {
        $data = $this->formatImageRequestData($validated);
        return FreepikAiService::startImageGeneration($data);
    }

    private function formatImageRequestData(array $validated): array
    {
        $data = [
            'prompt' => $this->buildPrompt($validated),
            'creative_detailing' => $validated['creative_detailing'],
            'model' => AiModel::getSnake($validated['ai_model_id']),
            'engine' => Engine::getSnake($validated['engine_id']),
        ];
        if (!empty($validated['colors'])) {
            $data['styling']['colors'] = $validated['colors'];
        }
        return $data;
    }

    private function buildPrompt(array $validated): string
    {
        $prompt = $this->formatIntroduction($validated['name'], $validated['breed_id']);
        $prompt .= ' ' . $this->formatThemes($validated['theme_ids']);
        $prompt .= ' ' . $validated['description'];
        return rtrim($prompt);
    }

    private function formatIntroduction(string $name, int $breedId): string
    {
        $breedName = Breed::getName($breedId);
        return "A cow named '$name' of the '$breedName' breed.";
    }

    private function formatThemes(?array $themeIds): string
    {
        $themes = Theme::getNames($themeIds ?? []);
        return $themes ? "It reflects the following themes: $themes." : '';
    }

    private function save(array $validated)
    {
        $cow = auth()->user()->cows()->create($validated);
        $cow->themes()->attach($validated['theme_ids'] ?? []);
        $cow->colors()->createMany($validated['colors'] ?? []);
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}
