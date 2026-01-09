<?php

namespace App\Services;

use Illuminate\Http\Client\Response;
use Illuminate\Support\Facades\Http;

class FreepikAiService
{
    public static function startImageGeneration(array $cowData): string
    {
        $data = $cowData + static::getDefaultRequestData();
        $response = static::sendRequest($data);
        static::handleResponseStatus($response);
        return $response->json('data.task_id');
    }

    private static function getDefaultRequestData(): array
    {
        return [
            'webhook_url' => config('services.freepik.webhook_url'),
            'resolution' => '1k',
            'aspect_ratio' => 'square_1_1',
            'fixed_generation' => false,
            'filter_nsfw' => true,
        ];
    }

    private static function sendRequest(array $data): Response
    {
        logger()->info('Freepik API request', $data);
        return Http::withHeaders([
            'x-freepik-api-key' => config('services.freepik.key'),
        ])->post(config('services.freepik.api_url'), $data);
    }

    private static function handleResponseStatus(Response $response): void
    {
        if ($response->failed()) {
            logger()->error('Freepik API error', $response->json());
            abort(500, 'Freepik API error');
        }
        logger()->info('Freepik API response', $response->json());
    }
}
