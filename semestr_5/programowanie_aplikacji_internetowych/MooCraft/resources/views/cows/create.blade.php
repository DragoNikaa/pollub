<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
            {{ __('Generator') }}
        </h2>
    </x-slot>

    <div class="py-12">
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8">
            <form method="POST" action="{{ route('cows.store') }}"
                  class="px-6 py-4 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-800 overflow-hidden shadow-md sm:rounded-lg">
                @csrf

                <!-- Name -->
                <div>
                    <x-input-label for="name" :value="__('Name')"/>
                    <x-text-input id="name" class="block mt-1 w-full" name="name" :value="old('name')"
                                  required minlength="3" maxlength="255" autofocus autocomplete="off"
                                  placeholder="e.g. StackOverMoo"/>
                    <x-input-error :messages="$errors->get('name')" class="mt-2"/>
                </div>

                <!-- Breed -->
                <div class="block mt-4">
                    <x-input-label class="mb-1" :value="__('Breed')"/>
                    <div class="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-6 gap-1">
                        @foreach ($breeds as $breed)
                            <label for="breed{{ $breed->id }}" class="inline-flex items-center">
                                <input id="breed{{ $breed->id }}" type="radio"
                                       class=" dark:bg-gray-900 border-gray-300 dark:border-gray-700 text-indigo-600 shadow-sm focus:ring-indigo-500 dark:focus:ring-indigo-600 dark:focus:ring-offset-gray-800"
                                       name="breed_id" value="{{ $breed->id }}" required
                                    @checked(old('breed_id') == $breed->id)>
                                <span class="ms-2 text-sm text-gray-600 dark:text-gray-400">
                                    {{ __($breed->name) }}
                                </span>
                            </label>
                        @endforeach
                    </div>
                    <x-input-error :messages="$errors->get('breed_id')" class="mt-2"/>
                </div>

                <!-- Themes -->
                <div class="block mt-4">
                    <x-input-label class="mb-1" :value="__('Themes')"/>
                    <div class="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-6 gap-1">
                        @foreach ($themes as $theme)
                            <label for="theme{{ $theme->id }}" class="inline-flex items-center">
                                <input id="theme{{ $theme->id }}" type="checkbox"
                                       class="rounded dark:bg-gray-900 border-gray-300 dark:border-gray-700 text-indigo-600 shadow-sm focus:ring-indigo-500 dark:focus:ring-indigo-600 dark:focus:ring-offset-gray-800"
                                       name="theme_ids[]" value="{{ $theme->id }}"
                                    @checked(in_array($theme->id, old('theme_ids', [])))>
                                <span class="ms-2 text-sm text-gray-600 dark:text-gray-400">
                                    {{ __($theme->name) }}
                                </span>
                            </label>
                        @endforeach
                    </div>
                    <x-input-error :messages="collect($errors->get('theme_ids.*'))->flatten()->all()" class="mt-2"/>
                    <x-input-error :messages="$errors->get('theme_ids')" class="mt-2"/>
                </div>

                <!-- Colors -->
                <x-input-label class="mt-4" :value="__('Colors')"/>
                <div class="grid gap-1">
                    @for ($i = 0; $i < 5; $i++)
                        <div id="color{{ $i }}"
                             class="grid grid-cols-[1fr_2fr_2fr] gap-4 md:gap-10 items-center hidden">
                            <!-- Choose -->
                            <label for="color{{ $i }}_choose" class="inline-flex items-center">
                                <input id="color{{ $i }}_choose" type="checkbox"
                                       class="rounded dark:bg-gray-900 border-gray-300 dark:border-gray-700 text-indigo-600 shadow-sm focus:ring-indigo-500 dark:focus:ring-indigo-600 dark:focus:ring-offset-gray-800"
                                    @checked(count(old('colors', [])) > $i)>
                                <span class="ms-2 text-sm text-gray-600 dark:text-gray-400">
                                    {{ __('Choose color') }}&nbsp;#{{ $i + 1 }}
                                </span>
                            </label>

                            <!-- Value -->
                            <div>
                                <x-input-label for="color{{ $i }}_value" :value="__('Value')"/>
                                <x-text-input id="color{{ $i }}_value"
                                              class="block mt-1 w-full h-10 disabled:opacity-50 disabled:bg-gray-200 disabled:dark:bg-gray-900 disabled:cursor-not-allowed"
                                              type="color" name="colors[{{ $i }}][color]"
                                              :value='old("colors.$i.color")' required/>
                                <x-input-error :messages="$errors->get('colors.' . $i . '.color')" class="mt-2"/>
                            </div>

                            <!-- Weight -->
                            <div>
                                <x-input-label for="color{{ $i }}_weight" :value="__('Weight')"/>
                                <x-text-input id="color{{ $i }}_weight"
                                              class="block mt-1 w-full disabled:opacity-50 disabled:bg-gray-200 disabled:dark:bg-gray-900 disabled:cursor-not-allowed"
                                              type="number" name="colors[{{ $i }}][weight]"
                                              :value='old("colors.$i.weight", 0.5)'
                                              required min="0.05" max="1" step="0.05"/>
                                <x-input-error :messages="$errors->get('colors.' . $i . '.weight')" class="mt-2"/>
                            </div>
                        </div>
                    @endfor
                </div>
                <x-input-error :messages="$errors->get('colors')" class="mt-2"/>

                <!-- Creative Detailing -->
                <div class="block mt-4">
                    <x-input-label for="creative_detailing" :value="__('Creative Detailing')"/>
                    <x-text-input id="creative_detailing" class="block mt-1 w-full" type="number"
                                  name="creative_detailing" :value="old('creative_detailing', 33)"
                                  required min="0" max="100"/>
                    <x-input-error :messages="$errors->get('creative_detailing')" class="mt-2"/>
                </div>

                <div class="mt-4 grid grid-cols-2 gap-4 md:gap-10">
                    <!-- AI Model -->
                    <div class="">
                        <x-input-label for="ai_model" :value="__('Model')"/>
                        <select id="ai_model"
                                class="block mt-1 w-full border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 focus:border-indigo-500 dark:focus:border-indigo-600 focus:ring-indigo-500 dark:focus:ring-indigo-600 rounded-md shadow-sm"
                                name="ai_model_id">
                            @foreach ($aiModels as $aiModel)
                                <option value="{{ $aiModel->id }}" @selected(old('ai_model_id', 1) == $aiModel->id)>
                                    {{ $aiModel->name }}
                                </option>
                            @endforeach
                        </select>
                        <x-input-error :messages="$errors->get('ai_model_id')" class="mt-2"/>
                    </div>

                    <!-- Engine -->
                    <div class="">
                        <x-input-label for="engine" :value="__('Engine')"/>
                        <select id="engine"
                                class="block mt-1 w-full border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 focus:border-indigo-500 dark:focus:border-indigo-600 focus:ring-indigo-500 dark:focus:ring-indigo-600 rounded-md shadow-sm"
                                name="engine_id">
                            @foreach ($engines as $engine)
                                <option value="{{ $engine->id }}" @selected(old('engine_id', 1) == $engine->id)>
                                    {{ $engine->name }}
                                </option>
                            @endforeach
                        </select>
                        <x-input-error :messages="$errors->get('engine_id')" class="mt-2"/>
                    </div>
                </div>

                <!-- Description -->
                <x-input-label for="description" class="mt-4" :value="__('Description')"/>
                <textarea id="description"
                          class="block mt-1 w-full h-40 sm:h-24 border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 focus:border-indigo-500 dark:focus:border-indigo-600 focus:ring-indigo-500 dark:focus:ring-indigo-600 rounded-md shadow-sm"
                          name="description" minlength="3"
                          placeholder="Describe a ridiculous cow that studies computer science, hasn't slept for 48 hours, and is fighting bugs in code instead of flies...">{{ old('description') }}</textarea>
                <x-input-error :messages="$errors->get('description')" class="mt-2"/>

                <div class="flex items-center justify-end mt-4">
                    <x-primary-button>
                        {{ __('Generate') }}
                    </x-primary-button>
                </div>
            </form>
        </div>
    </div>
</x-app-layout>
@vite('resources/js/pages/cows.js')
