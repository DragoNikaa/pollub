<?php

namespace App\Http\Middleware;

use App\Models\Notification;
use App\Models\User;
use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class LoadUserToast
{
    /**
     * Handle an incoming request.
     *
     * @param \Closure(\Illuminate\Http\Request): (\Symfony\Component\HttpFoundation\Response) $next
     */
    public function handle(Request $request, Closure $next): Response
    {
        if ($user = auth()->user()) {
            $this->loadToast($user);
        }
        return $next($request);
    }

    private function loadToast(User $user): void
    {
        session()->forget('toast');
        $toast = $user->getLatestUnreadNotification();
        if ($toast) {
            $this->putInSession($toast);
            $toast->update(['read' => true]);
        }
    }

    private function putInSession(Notification $toast): void
    {
        session()->put('toast', [
            'message' => $toast->message,
        ]);
    }
}
