<?php

namespace App\Http\Controllers;

use App\Models\Cow;
use App\Models\User;
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
        $cow = Cow::getByFreepikTaskId($data['task_id']);
        return $this->handleImageStatus($data, $cow->user, $cow->name);
    }

    private function getRequestData(Request $request): array
    {
        $data = $request->json()->all();
        logger()->info('Freepik webhook', $data);
        return $data;
    }

    private function handleImageStatus(array $data, User $user, string $cowName): JsonResponse
    {
        switch ($data['status']) {
            case 'COMPLETED':
                $this->saveImage($data['generated'][0], $data['task_id']);
                $user->addNotification(
                    "Moorvelous news! 🐄 Your cow '$cowName' has just been created and is waiting for you in the gallery. Go check out your new buddy!"
                );
                return response()->json(['action' => 'saved']);
            case 'FAILED':
                Cow::deleteByFreepikTaskId($data['task_id']);
                $user->addNotification(
                    "Oh no! 🐄 Your cow '$cowName' didn't make it this time. Don't worry – you can try again and give it another chance!"
                );
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
