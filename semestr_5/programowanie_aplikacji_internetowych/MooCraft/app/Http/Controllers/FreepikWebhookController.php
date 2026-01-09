<?php

namespace App\Http\Controllers;

use App\Models\Cow;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Storage;

class FreepikWebhookController extends Controller
{
    /**
     * Handle the incoming request.
     */
    public function __invoke(Request $request): JsonResponse
    {
        $data = $this->getRequestData($request);
        return $this->handleImageStatus($data);
    }

    private function getRequestData(Request $request): array
    {
        $data = $request->json()->all();
        logger()->info('Freepik webhook', $data);
        return $data;
    }

    private function handleImageStatus(array $data): JsonResponse
    {
        switch ($data['status']) {
            case 'COMPLETED':
                $this->saveImage($data['generated'][0], $data['task_id']);
                return response()->json(['action' => 'saved']);
            case 'FAILED':
                Cow::deleteByFreepikTaskId($data['task_id']);
                return response()->json(['action' => 'deleted']);
            default:
                return response()->json(['action' => 'ignored']);
        }
    }

    private function saveImage(string $url, string $taskId): void
    {
        $imageId = uniqid();
        $image = Http::get($url)->body();
        Storage::disk('public')->put("cows/$imageId.png", $image);
        Cow::updateImageId($taskId, $imageId);
    }
}
