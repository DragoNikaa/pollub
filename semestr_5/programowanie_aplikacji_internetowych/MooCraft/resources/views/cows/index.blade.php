<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
            {{ __('Gallery') }}
        </h2>
    </x-slot>

    <div class="py-12">
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
            @foreach ($cows as $cow)
                <div
                    class="text-sm text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-800 overflow-hidden shadow-md sm:rounded-lg">
                    <!-- Image -->
                    <img src="{{ $cow->getImagePath() }}" alt="Cow {{ $cow->name }}">

                    <div class="px-6 py-4 space-y-4">
                        <div class="flex items-center justify-between gap-6">
                            <!-- Name -->
                            <h2 class="font-semibold text-2xl">
                                {{ $cow->name }}
                            </h2>

                            @can('update', $cow)
                                <x-primary-button as="a" href="{{ route('cows.edit', $cow) }}" class="h-8 gap-2">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                         stroke-width="1.5" stroke="currentColor" class="size-6">
                                        <path stroke-linecap="round" stroke-linejoin="round"
                                              d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"/>
                                    </svg>
                                    {{ __('Edit') }}
                                </x-primary-button>
                            @endcan
                        </div>

                        <!-- User -->
                        <div>
                            <span class="font-semibold text-gray-500 dark:text-gray-400">
                                {{ __('Rancher') }}:
                            </span>
                            {{ $cow->user->name }}
                        </div>

                        <div class="grid grid-cols-2 gap-4">
                            <!-- Breed -->
                            <div>
                                <span class="font-semibold text-gray-500 dark:text-gray-400">
                                    {{ __('Breed') }}:
                                </span><br>
                                {{ $cow->breed->name }}
                            </div>

                            <!-- Creative Detailing -->
                            <div>
                                <span class="font-semibold text-gray-500 dark:text-gray-400">
                                    {{ __('Creative Detailing') }}:
                                </span><br>
                                {{ $cow->creative_detailing }}
                            </div>

                            <!-- Themes -->
                            <div>
                                <span class="font-semibold text-gray-500 dark:text-gray-400">
                                    {{ __('Themes') }}:
                                </span>
                                <ul>
                                    @forelse ($cow->themes as $theme)
                                        <li>{{ $theme->name }}</li>
                                    @empty
                                        <li>–</li>
                                    @endforelse
                                </ul>
                            </div>

                            <!-- Colors -->
                            <div>
                                <span class="font-semibold text-gray-500 dark:text-gray-400">
                                    {{ __('Colors') }}:
                                </span>
                                <ul>
                                    @forelse ($cow->colors as $color)
                                        <li class="flex items-center gap-2">
                                            <!-- Value -->
                                            <span class="w-11 h-3 rounded" style="background-color: {{ $color->color }}"
                                                  title="{{ $color->color }}"></span>

                                            <!-- Weight -->
                                            {{ $color->weight * 100 }}%
                                        </li>
                                    @empty
                                        <li>–</li>
                                    @endforelse
                                </ul>
                            </div>

                            <!-- AI Model -->
                            <div>
                                <span class="font-semibold text-gray-500 dark:text-gray-400">
                                    {{ __('Model') }}:
                                </span><br>
                                {{ $cow->aiModel->name }}
                            </div>

                            <!-- Engine -->
                            <div>
                                <span class="font-semibold text-gray-500 dark:text-gray-400">
                                    {{ __('Engine') }}:
                                </span><br>
                                {{ $cow->engine->name }}
                            </div>
                        </div>

                        <!-- Description -->
                        <div>
                            <span class="font-semibold text-gray-500 dark:text-gray-400">
                                {{ __('Description') }}:
                            </span><br>
                            {{ $cow->description ?? '–' }}
                        </div>

                        @can('delete', $cow)
                            <form method="POST" action="{{ route('cows.destroy', $cow) }}"
                                  onsubmit="return confirm('{{ __("Are you sure you want to delete cow") }} \'{{ $cow->name }}\'?')"
                                  class="flex items-center justify-end">
                                @csrf
                                @method('DELETE')

                                <x-danger-button class="h-8 gap-2">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                         stroke-width="1.5" stroke="currentColor" class="size-6">
                                        <path stroke-linecap="round" stroke-linejoin="round"
                                              d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"/>
                                    </svg>
                                    {{ __('Delete') }}
                                </x-danger-button>
                            </form>
                        @endcan
                    </div>
                </div>
            @endforeach
        </div>

        <div class="flex items-center justify-center mt-8">
            {{ $cows->links() }}
        </div>
    </div>
</x-app-layout>
