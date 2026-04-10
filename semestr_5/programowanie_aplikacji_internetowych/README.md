# MooCraft
Ta aplikacja webowa wykorzystuje potęgę sztucznej inteligencji do generowania zabawnych, kreatywnych, a czasem wręcz absurdalnych obrazków... krów!? 🐄

Niezależnie od tego, czy marzysz o neonowej krowie latającej na deskorolce, czy mitycznym byku tańczącym w kosmosie, tutaj możesz zrealizować swoje najdziwniejsze pomysły. Każdy obrazek jest unikalny, a Ty masz pełną kontrolę nad tym, jak bardzo abstrakcyjny będzie efekt końcowy. To miejsce, w którym sztuka łączy się z humorem i odrobiną cyfrowego szaleństwa, a każdy pomysł zamienia się w arcydzieło (lub totalnego mema)!

### Backend
Zbudowany w oparciu o framework [Laravel](https://laravel.com/) ([PHP](https://www.php.net/)) i architekturę [MVC](https://laravel.com/learn/getting-started-with-laravel/what-is-mvc). Wykorzystuje mapowanie obiektowo-relacyjne [Eloquent ORM](https://laravel.com/docs/13.x/eloquent) do współpracy z bazą danych [MySQL](https://www.mysql.com/). Generowanie obrazów realizowane jest poprzez integrację z zewnętrznym API AI – [Freepik Mystic](https://docs.freepik.com/api-reference/mystic/mystic).

### Frontend
Oparty na szablonach [Blade](https://laravel.com/docs/13.x/blade) i frameworku [Tailwind CSS](https://tailwindcss.com/), co zapewnia nowoczesny, intuicyjny i w pełni responsywny interfejs użytkownika.
<br><br>
## Funkcjonalności
- Generowanie zabawnych obrazków krów za pomocą rozbudowanego formularza.
- Przeglądanie galerii krów wszystkich użytkowników wraz z użytymi parametrami.
- Edycja i usuwanie własnych wygenerowanych krów.
- Powiadomienia o zmianie statusu generowania krowy przez AI.
- Podgląd charakterystyk oraz przeznaczenia dostępnych modeli i silników AI.
- Uwierzytelnianie i autoryzacja użytkowników.
- Automatyczne dostosowanie interfejsu do trybu systemowego (jasny/ciemny).
<br><br>
## Wymagania środowiskowe
Projekt został przetestowany na poniższych wersjach oprogramowania. Zaleca się korzystanie z identycznych lub nowszych, aby uniknąć problemów z kompatybilnością:
- [XAMPP](https://www.apachefriends.org/pl/download.html) (PHP, Apache, MySQL) 8.2.12
- [Composer](https://getcomposer.org/download/) 2.9.2
- [Node.js](https://nodejs.org/en/download) 24.12.0
- [ngrok](https://ngrok.com/download/windows) 3.35.0
<br><br>
## Instrukcja uruchomienia

### 1. Pobranie projektu
Umieść folder projektu [Moocraft](MooCraft) w wybranej lokalizacji, np. `C:\xampp\htdocs\MooCraft`.

### 2. Instalacja zależności
Otwórz terminal w folderze projektu i pobierz niezbędne pakiety:
- backendowe:
```bash
composer install
```
- frontendowe:
```bash
npm install
```

### 3. Konfiguracja pliku `.env`
1. Skopiuj plik z przykładową konfiguracją:
```bash
cp .env.example .env
```
2. Wygeneruj klucz szyfrujący aplikacji:
```bash
php artisan key:generate
```

### 4. Konfiguracja bazy danych
1. Uruchom **XAMPP Control Panel**.
2. Włącz moduły **Apache** oraz **MySQL**.
3. Przejdź do panelu [phpMyAdmin](http://localhost/phpmyadmin/).
4. Zaimportuj plik [moocraft.sql](moocraft.sql).

### 5. Konfiguracja Freepik API
1. Wejdź na [Freepik Developers Dashboard](https://www.freepik.com/developers/dashboard/api-key) (wymaga założenia konta).
2. Wygeneruj i skopiuj swój klucz API.
3. Wklej go do pliku `.env` wg wzoru:
```env
FREEPIK_API_KEY=twoj-klucz-api
```

### 6. Uruchomienie tunelu ngrok
1. Wejdź na [ngrok Dashboard](https://dashboard.ngrok.com/get-started/your-authtoken) (wymaga założenia konta).
2. Wygeneruj i skopiuj swój token autoryzacyjny.
3. Uwierzytelnij aplikację **ngrok** w swoim systemie:
```bash
ngrok config add-authtoken <twoj-token-autoryzacyjny>
```
4. Uruchom tunel:
```bash
ngrok http 8000
```
5. Skopiuj wygenerowany publiczny adres *Forwarding* i wklej go do pliku `.env` wg wzoru:
```env
FREEPIK_WEBHOOK_URL=https://twoj-publiczny-adres.ngrok-free.dev/api/webhooks/freepik
```

### 7. Linkowanie folderu plików
W folderze projektu wykonaj polecenie tworzące dowiązanie symboliczne między `storage/app/public` a `public/storage`:
```bash
php artisan storage:link
```

### 8. Uruchomienie aplikacji
W dwóch osobnych terminalach w folderze projektu uruchom:
- backend:
```bash
php artisan serve
```
- frontend:
```bash
npm run dev
```

### 9. Otwarcie aplikacji
Aplikacja będzie dostępna w przeglądarce pod adresem:<br>
http://localhost:8000
<br><br>
## Konta testowe
Do przetestowania aplikacji możesz użyć poniższych danych logowania:
| Użytkownik | Login                   | Hasło      |
|------------|-------------------------|------------|
| DragoNika  | `dragonika@example.com` | `password` |
| TestUser1  | `test1@example.com`     | `password` |
| TestUser2  | `test2@example.com`     | `password` |