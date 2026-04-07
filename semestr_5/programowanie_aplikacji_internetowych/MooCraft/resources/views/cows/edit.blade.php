<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
            {{ __('Edit') }}
        </h2>
    </x-slot>

    <div class="py-12">
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8">
            <form method="POST" action="{{ route('cows.update', $cow) }}"
                  class="px-6 py-4 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-800 overflow-hidden shadow-md sm:rounded-lg">
                @csrf
                @method('PATCH')

                <!-- Name -->
                <div>
                    <x-input-label for="name" :value="__('Name')"/>
                    <x-text-input id="name" class="block mt-1 w-full" name="name" :value="old('name', $cow->name)"
                                  required minlength="3" maxlength="255" autofocus autocomplete="off"
                                  placeholder="{{ __('e.g.') }} StackOverMoo"/>
                    <x-input-error :messages="$errors->get('name')" class="mt-2"/>
                </div>

                <div class="flex justify-end gap-4 mt-4">
                    <x-danger-button as="a" :href="route('cows.index')">
                        {{ __('Cancel') }}
                    </x-danger-button>
                    <x-primary-button>
                        {{ __('Update') }}
                    </x-primary-button>
                </div>
            </form>
        </div>
    </div>
</x-app-layout>
