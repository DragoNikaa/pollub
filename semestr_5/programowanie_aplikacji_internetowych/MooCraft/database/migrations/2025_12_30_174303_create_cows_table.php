<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('breeds', function (Blueprint $table) {
            $table->id();
            $table->string('name');
        });

        Schema::create('themes', function (Blueprint $table) {
            $table->id();
            $table->string('name');
        });

        Schema::create('ai_models', function (Blueprint $table) {
            $table->id();
            $table->string('name');
        });

        Schema::create('engines', function (Blueprint $table) {
            $table->id();
            $table->string('name');
        });

        Schema::create('cows', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->foreignId('breed_id')->constrained()->restrictOnDelete();
            $table->foreignId('ai_model_id')->constrained()->restrictOnDelete();
            $table->foreignId('engine_id')->constrained()->restrictOnDelete();
            $table->string('name');
            $table->unsignedTinyInteger('creative_detailing');
            $table->text('description')->nullable();
            $table->string('image_path')->nullable();
            $table->timestamps();
        });

        Schema::create('cow_theme', function (Blueprint $table) {
            $table->foreignId('cow_id')->constrained()->cascadeOnDelete();
            $table->foreignId('theme_id')->constrained()->restrictOnDelete();
            $table->primary(['cow_id', 'theme_id']);
        });

        Schema::create('colors', function (Blueprint $table) {
            $table->id();
            $table->foreignId('cow_id')->constrained()->cascadeOnDelete();
            $table->string('color', 7);
            $table->decimal('weight', 3, 2);
        });

    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('colors');
        Schema::dropIfExists('cow_theme');
        Schema::dropIfExists('cows');
        Schema::dropIfExists('engines');
        Schema::dropIfExists('ai_models');
        Schema::dropIfExists('themes');
        Schema::dropIfExists('breeds');
    }
};
