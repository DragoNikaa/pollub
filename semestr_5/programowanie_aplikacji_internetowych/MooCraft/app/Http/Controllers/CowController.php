<?php

namespace App\Http\Controllers;

use App\Models\AiModel;
use App\Models\Breed;
use App\Models\Engine;
use App\Models\Theme;
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
        $breeds = Breed::all()->sortBy('name');
        $themes = Theme::all()->sortBy('name');
        $aiModels = AiModel::all()->sortBy('name');
        $engines = Engine::all()->sortBy('name');

        return view('cows.create', compact('breeds', 'themes', 'aiModels', 'engines'));
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $validated = $this->validate($request);
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
            'colors.*.hex_code' => ['required', 'distinct', 'string', 'hex_color'],
            'colors.*.weight' => ['required', 'numeric', 'between:0.05,1'],

            'creative_detailing' => ['required', 'integer', 'between:0,100'],

            'ai_model_id' => ['required', 'integer', 'exists:ai_models,id'],
            'engine_id' => ['required', 'integer', 'exists:engines,id'],

            'description' => ['nullable', 'string', 'min:3'],
        ]);
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
