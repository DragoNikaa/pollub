<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
            {{ __('Home') }}
        </h2>
    </x-slot>

    <div class="py-12 space-y-12">
        <!-- Freepik Mystic -->
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8">
            <div
                class="p-6 space-y-6 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-800 overflow-hidden shadow-sm sm:rounded-lg">
                <p>
                    {{ __('This website lets you generate funny, creative, and sometimes
                           slightly ridiculous cow images. 🐄 Behind the scenes, we use the') }}
                    <b>Freepik Mystic</b>
                    {{ __(' AI image generator to make it all happen. The magic happens here – the
                           link below is just for curious minds who want to see what powers it.') }}
                </p>

                <x-primary-button as="a" href="https://docs.freepik.com/api-reference/mystic/mystic" target="_blank">
                    {{ __('View Freepik Mystic AI API documentation →') }}
                </x-primary-button>
            </div>
        </div>

        <!-- AI Models -->
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8">
            <div
                class="p-6 space-y-4 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-800 overflow-hidden shadow-sm sm:rounded-lg">
                <h2 class="font-semibold text-2xl">
                    {{ __('Models') }}
                </h2>

                <ul class="space-y-4">
                    @foreach ($aiModels as $aiModel)
                        <li class="p-4 border border-gray-100 dark:border-gray-700 rounded-lg">
                            <h3 class="font-semibold text-lg text-indigo-600 dark:text-indigo-400">
                                {{ $aiModel->name }}
                            </h3>
                            <p>{{ $aiModel->description }}</p>
                        </li>
                    @endforeach
                </ul>
            </div>
        </div>

        <!-- Engines -->
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8">
            <div
                class="p-6 space-y-4 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-800 overflow-hidden shadow-sm sm:rounded-lg">
                <h2 class="font-semibold text-2xl">
                    {{ __('Engines') }}
                </h2>

                <ul class="space-y-4">
                    @foreach ($engines as $engine)
                        <li class="p-4 border border-gray-100 dark:border-gray-700 rounded-lg">
                            <h3 class="font-semibold text-lg text-indigo-600 dark:text-indigo-400">
                                {{ $engine->name }}
                            </h3>
                            <p>{{ $engine->description }}</p>
                        </li>
                    @endforeach
                </ul>
            </div>
        </div>
    </div>
</x-app-layout>
