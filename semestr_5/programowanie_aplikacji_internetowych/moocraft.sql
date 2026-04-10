-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 10, 2026 at 04:57 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `moocraft`
--
CREATE DATABASE IF NOT EXISTS `moocraft` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `moocraft`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ai_models`
--

CREATE TABLE `ai_models` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ai_models`
--

INSERT INTO `ai_models` (`id`, `name`, `description`) VALUES
(1, 'Realism', 'With a more realistic color palette. It tries to give an extra boost of reality to your images, a kind of \"less AI look\". Works especially well with photographs but also magically works with illustrations too. IMPORTANT: you should use Zen, Flexible or Fluid if you are trying to generate something that is really fantastic, Realism may not follow your prompt well.'),
(2, 'Zen', 'For smoother, basic, and cleaner results. Fewer objects in the scene and less intricate details. The softer looking one.'),
(3, 'Flexible', 'Good prompt adherence. However, it has results that are a bit more HDR and saturated than Realism or Fluid. It\'s especially good with illustrations, fantastical prompts, and for diving into the latent space in search of very specific visual styles.'),
(4, 'Fluid', 'The model that adheres best to prompts with great average quality for all kind of images. It can generate really creative images! It will always follow your input no matter what.'),
(5, 'Super Real', 'If reality is your priority, this is your model. Nearly as versatile as Flexible, it excels in realism outperforming Editorial Portraits in medium shots, though not as strong for close-ups.'),
(6, 'Editorial Portraits', 'The most amazing state-of-the-art generator for editorial portraits. You have never seen a level of realism like this before. Perfect for hyperrealistic close-up or medium shots. Unfortunately, in wide or distant shots, it generates anatomical problems and artifacts… but for close-ups, it is simply the best on the market. Tip: use the longest and most explanatory prompts possible, they really suit it well!');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `breeds`
--

CREATE TABLE `breeds` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `breeds`
--

INSERT INTO `breeds` (`id`, `name`) VALUES
(1, 'Angus'),
(2, 'Ayrshire'),
(3, 'Brahman'),
(4, 'Brown Swiss'),
(5, 'Charolais'),
(6, 'Dexter'),
(7, 'Guernsey'),
(8, 'Hereford'),
(9, 'Highland'),
(10, 'Holstein'),
(11, 'Jersey'),
(12, 'Texas Longhorn');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cache`
--

CREATE TABLE `cache` (
  `key` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cache_locks`
--

CREATE TABLE `cache_locks` (
  `key` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `colors`
--

CREATE TABLE `colors` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `cow_id` bigint(20) UNSIGNED NOT NULL,
  `color` varchar(7) NOT NULL,
  `weight` decimal(3,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `colors`
--

INSERT INTO `colors` (`id`, `cow_id`, `color`, `weight`) VALUES
(1, 2, '#f29718', 0.30),
(2, 2, '#3dbdae', 0.25),
(3, 3, '#e100ff', 0.60),
(4, 3, '#7300ff', 0.25),
(5, 3, '#00e1ff', 0.40),
(6, 3, '#11ff00', 0.55),
(7, 6, '#bc1515', 0.10),
(8, 6, '#64bd28', 0.10),
(9, 8, '#c76a00', 0.05),
(10, 8, '#000000', 0.20),
(11, 8, '#ff0000', 0.05),
(12, 10, '#84d0f1', 0.20),
(13, 10, '#cb65cd', 0.35),
(14, 11, '#ff0000', 0.50),
(15, 11, '#00ff59', 0.50),
(16, 11, '#1100ff', 0.50),
(17, 11, '#e380ff', 0.50),
(18, 11, '#4400ff', 0.50),
(19, 13, '#7ccbc9', 0.20),
(20, 13, '#ffffff', 0.30),
(21, 13, '#ddc446', 0.05);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cows`
--

CREATE TABLE `cows` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `freepik_task_id` varchar(255) NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `breed_id` bigint(20) UNSIGNED NOT NULL,
  `ai_model_id` bigint(20) UNSIGNED NOT NULL,
  `engine_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `creative_detailing` tinyint(3) UNSIGNED NOT NULL,
  `description` text DEFAULT NULL,
  `image_id` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `cows`
--

INSERT INTO `cows` (`id`, `freepik_task_id`, `user_id`, `breed_id`, `ai_model_id`, `engine_id`, `name`, `creative_detailing`, `description`, `image_id`, `created_at`, `updated_at`) VALUES
(1, '07b8e933-6f1c-4ff5-a7a0-1ecb2b907cb4', 1, 9, 4, 2, 'StackOverMoo', 80, 'A stressed cow finishing a computer science project at 3 a.m., energy drinks everywhere, glowing laptop screen.', '69668125d8246', '2026-01-13 16:30:07', '2026-01-13 16:30:15'),
(2, 'f61cb193-d344-4a94-8e34-07c8d1dcb1c6', 1, 2, 1, 3, 'Mooverick', 33, 'A cow wearing sunglasses and riding a skateboard through a city at sunset, ultra realistic.', '6966851163ac2', '2026-01-13 16:46:52', '2026-01-13 16:46:57'),
(3, 'c14802a8-3251-4327-99e1-77d43218bb1c', 1, 12, 3, 4, 'Cow-2077', 50, 'A cyberpunk cow with neon lights, futuristic city background.', '6966865879295', '2026-01-13 16:52:15', '2026-01-13 16:52:25'),
(5, 'fc41cf09-3c5f-41c8-8b48-7a4332749bdd', 2, 5, 5, 3, 'Bumper', 25, 'A cow stuck in traffic, sitting in a small car, photorealistic.', '696687bed1d7a', '2026-01-13 16:58:16', '2026-01-13 16:58:23'),
(6, 'fb8d539b-149d-4769-845d-3bca6b67e19f', 3, 6, 2, 1, 'MilkFluencer', 33, 'A cow as a TikTok influencer, ring light, smartphone, trendy outfit.', '6966893fd68cc', '2026-01-13 17:04:44', '2026-01-13 17:04:49'),
(7, 'c65dc4ed-999c-4770-aa5c-0b0e3755fa3e', 1, 10, 2, 4, 'Berta', 99, NULL, '696689f46e298', '2026-01-13 17:07:44', '2026-01-13 17:07:48'),
(8, '86eacc05-2608-4aaa-9f0e-4bd877335ea2', 1, 1, 3, 1, 'Udder Fury', 71, 'A medieval dragon that is actually just a cow in a costume.', '69668ae5b383f', '2026-01-13 17:11:45', '2026-01-13 17:11:50'),
(9, '6194e2cc-db37-481f-a902-a49ce53b2b59', 1, 4, 2, 2, 'Moongogh', 100, 'A cow painted in the style of Van Gogh\'s Starry Night.', '69668b65d47e6', '2026-01-13 17:13:53', '2026-01-13 17:13:58'),
(10, 'dd381f38-1b4a-448d-bc77-e20615372352', 1, 3, 1, 1, 'Coffice', 77, 'A cow working remotely from home, pajamas, coffee mug, messy desk.', '69668c350d049', '2026-01-13 17:17:19', '2026-01-13 17:17:25'),
(11, '9ee97e9d-aabc-4f87-b75c-f8982c6cca4d', 1, 9, 2, 2, 'Daisy', 90, NULL, '69668caec3991', '2026-01-13 17:19:23', '2026-01-13 17:19:27'),
(12, 'b78eb410-b532-4e4e-b23e-cd2b9f4cb543', 2, 12, 4, 4, 'Agent Bovine', 33, 'A cow as a secret agent in a tuxedo, holding a tiny espresso, James Bond style.', '69668e1a05a0c', '2026-01-13 17:25:25', '2026-01-13 17:25:30'),
(13, 'bfbc2bdd-be9a-4f7d-8a31-b41806302907', 1, 4, 4, 3, 'Cloudy', 20, 'A cow made entirely of clouds floating in the sky.', '69668eb1ce732', '2026-01-13 17:27:55', '2026-01-13 17:28:02'),
(15, '31ed7e47-14ff-42e0-b75a-3061ff220f7d', 1, 9, 2, 1, 'Ixi', 33, 'A funny cartoon scene of a cow and a dog hanging out together in a sunny meadow.', '6966a5f0d90c5', '2026-01-13 19:07:09', '2026-01-13 19:07:13');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cow_theme`
--

CREATE TABLE `cow_theme` (
  `cow_id` bigint(20) UNSIGNED NOT NULL,
  `theme_id` bigint(20) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `cow_theme`
--

INSERT INTO `cow_theme` (`cow_id`, `theme_id`) VALUES
(1, 1),
(1, 15),
(2, 3),
(2, 9),
(2, 16),
(3, 1),
(3, 4),
(3, 5),
(3, 10),
(3, 12),
(3, 13),
(6, 2),
(6, 7),
(6, 11),
(7, 6),
(7, 8),
(7, 9),
(7, 10),
(7, 11),
(8, 1),
(8, 6),
(8, 8),
(9, 1),
(9, 2),
(10, 5),
(10, 10),
(11, 3),
(11, 4),
(11, 5),
(11, 6),
(11, 7),
(11, 9),
(11, 10),
(11, 11),
(11, 12),
(11, 14),
(11, 15),
(12, 13),
(12, 14),
(15, 6),
(15, 9),
(15, 11);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `engines`
--

CREATE TABLE `engines` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `engines`
--

INSERT INTO `engines` (`id`, `name`, `description`) VALUES
(1, 'Automatic', 'Default choice.'),
(2, 'Illusio', 'For smoother illustrations, landscapes, and nature. The softer looking one.'),
(3, 'Sharpy', 'Better for realistic images like photographs and for a more grainy look. It provides the sharpest and most detailed images. If you use it for illustrations it will give them more texture and a less softer look.'),
(4, 'Sparkle', 'Also good for realistic images. It\'s a middle ground between Illusio and Sharpy.');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `connection` text NOT NULL,
  `queue` text NOT NULL,
  `payload` longtext NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `jobs`
--

CREATE TABLE `jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `queue` varchar(255) NOT NULL,
  `payload` longtext NOT NULL,
  `attempts` tinyint(3) UNSIGNED NOT NULL,
  `reserved_at` int(10) UNSIGNED DEFAULT NULL,
  `available_at` int(10) UNSIGNED NOT NULL,
  `created_at` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `job_batches`
--

CREATE TABLE `job_batches` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `total_jobs` int(11) NOT NULL,
  `pending_jobs` int(11) NOT NULL,
  `failed_jobs` int(11) NOT NULL,
  `failed_job_ids` longtext NOT NULL,
  `options` mediumtext DEFAULT NULL,
  `cancelled_at` int(11) DEFAULT NULL,
  `created_at` int(11) NOT NULL,
  `finished_at` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '0001_01_01_000000_create_users_table', 1),
(2, '0001_01_01_000001_create_cache_table', 1),
(3, '0001_01_01_000002_create_jobs_table', 1),
(4, '2025_12_30_174303_create_cows_table', 1),
(5, '2026_01_04_210418_create_personal_access_tokens_table', 1),
(6, '2026_01_12_155323_create_notifications_table', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `notifications`
--

CREATE TABLE `notifications` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `message` text NOT NULL,
  `read` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `user_id`, `message`, `read`, `created_at`, `updated_at`) VALUES
(1, 1, 'Mooment of patience… 🐄 Your cow \'StackOverMoo\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 16:30:07', '2026-01-13 16:30:07'),
(2, 1, 'Moorvelous news! 🐄 Your cow \'StackOverMoo\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 16:30:15', '2026-01-13 16:30:15'),
(3, 1, 'Mooment of patience… 🐄 Your cow \'Mooverick\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 16:46:52', '2026-01-13 16:46:52'),
(4, 1, 'Moorvelous news! 🐄 Your cow \'Mooverick\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 16:46:57', '2026-01-13 16:47:03'),
(5, 1, 'Mooment of patience… 🐄 Your cow \'Cow-2077\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 16:52:15', '2026-01-13 16:52:15'),
(6, 1, 'Moorvelous news! 🐄 Your cow \'Cow-2077\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 16:52:25', '2026-01-13 16:52:28'),
(7, 2, 'Mooment of patience… 🐄 Your cow \'Bumper\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 16:56:26', '2026-01-13 16:56:26'),
(8, 2, 'Mooment of patience… 🐄 Your cow \'Bumper\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 16:58:16', '2026-01-13 16:58:17'),
(9, 2, 'Moorvelous news! 🐄 Your cow \'Bumper\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 16:58:23', '2026-01-13 16:58:32'),
(10, 3, 'Mooment of patience… 🐄 Your cow \'MilkFluencer\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:04:44', '2026-01-13 17:04:45'),
(11, 3, 'Moorvelous news! 🐄 Your cow \'MilkFluencer\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:04:49', '2026-01-13 17:04:55'),
(12, 1, 'Mooment of patience… 🐄 Your cow \'Berta\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:07:44', '2026-01-13 17:07:45'),
(13, 1, 'Moorvelous news! 🐄 Your cow \'Berta\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:07:48', '2026-01-13 17:07:49'),
(14, 1, 'Mooment of patience… 🐄 Your cow \'Udder Fury\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:11:45', '2026-01-13 17:11:45'),
(15, 1, 'Moorvelous news! 🐄 Your cow \'Udder Fury\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:11:50', '2026-01-13 17:11:53'),
(16, 1, 'Mooment of patience… 🐄 Your cow \'Moongogh\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:13:53', '2026-01-13 17:13:54'),
(17, 1, 'Moorvelous news! 🐄 Your cow \'Moongogh\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:13:59', '2026-01-13 17:13:59'),
(18, 1, 'Mooment of patience… 🐄 Your cow \'Coffice\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:17:19', '2026-01-13 17:17:19'),
(19, 1, 'Moorvelous news! 🐄 Your cow \'Coffice\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:17:25', '2026-01-13 17:17:27'),
(20, 1, 'Mooment of patience… 🐄 Your cow \'Daisy\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:19:23', '2026-01-13 17:19:23'),
(21, 1, 'Moorvelous news! 🐄 Your cow \'Daisy\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:19:27', '2026-01-13 17:19:27'),
(22, 2, 'Mooment of patience… 🐄 Your cow \'Agent Bovine\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:25:25', '2026-01-13 17:25:25'),
(23, 2, 'Moorvelous news! 🐄 Your cow \'Agent Bovine\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:25:30', '2026-01-13 17:25:30'),
(24, 1, 'Mooment of patience… 🐄 Your cow \'Cloudy\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 17:27:55', '2026-01-13 17:27:55'),
(25, 1, 'Moorvelous news! 🐄 Your cow \'Cloudy\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 17:28:02', '2026-01-13 17:28:02'),
(26, 2, 'Oh no! 🐄 Your cow \'Bumper\' didn\'t make it this time. Don\'t worry – you can try again and give it another chance!', 1, '2026-01-13 17:36:42', '2026-01-13 17:41:23'),
(27, 1, 'Mooment of patience… 🐄 Your cow \'Beata\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 19:05:58', '2026-01-13 19:05:58'),
(28, 1, 'Mooment of patience… 🐄 Your cow \'Ixi\' is being generated. We\'ll let you know when it\'s ready!', 1, '2026-01-13 19:07:09', '2026-01-13 19:07:09'),
(29, 1, 'Moorvelous news! 🐄 Your cow \'Ixi\' has just been created and is waiting for you in the gallery. Go check out your new buddy!', 1, '2026-01-13 19:07:13', '2026-01-13 19:07:26'),
(30, 1, 'Name change complete. 🐄 Say hello to cow \'Beatad\'!', 1, '2026-01-13 19:07:39', '2026-01-13 19:07:39'),
(31, 1, 'The herd just got smaller… 🐄 Cow \'Beatad\' has been deleted!', 1, '2026-01-13 19:07:47', '2026-01-13 19:07:47');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` text NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sessions`
--

CREATE TABLE `sessions` (
  `id` varchar(255) NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `payload` longtext NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `themes`
--

CREATE TABLE `themes` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `themes`
--

INSERT INTO `themes` (`id`, `name`) VALUES
(1, 'Abstract'),
(2, 'Art'),
(3, 'Chaos'),
(4, 'Disco'),
(5, 'Fantasy'),
(6, 'Magic'),
(7, 'Meme'),
(8, 'Mythical'),
(9, 'Nature'),
(10, 'Pixel'),
(11, 'Rainbow'),
(12, 'Robot'),
(13, 'Sci-fi'),
(14, 'Space'),
(15, 'Student'),
(16, 'Vintage');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'DragoNika', 'dragonika@example.com', NULL, '$2y$12$/EQFCj3cjKtrVblnwaV01.DL5jEjatOMCHgBE.Q94aS6WFOHE13/O', NULL, NULL, NULL),
(2, 'TestUser1', 'test1@example.com', NULL, '$2y$12$JnrfDdmpIKRNHqKVOilWsuvrU5il9LFXSWu4PvrM5qn6sUXKqR21u', NULL, NULL, NULL),
(3, 'TestUser2', 'test2@example.com', NULL, '$2y$12$z17Pgjje.JS2IqyTZcaX7Oj7pOrOOwjWMHhALlm.TRmH9OxHi0rE6', NULL, NULL, NULL);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `ai_models`
--
ALTER TABLE `ai_models`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `breeds`
--
ALTER TABLE `breeds`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `cache`
--
ALTER TABLE `cache`
  ADD PRIMARY KEY (`key`);

--
-- Indeksy dla tabeli `cache_locks`
--
ALTER TABLE `cache_locks`
  ADD PRIMARY KEY (`key`);

--
-- Indeksy dla tabeli `colors`
--
ALTER TABLE `colors`
  ADD PRIMARY KEY (`id`),
  ADD KEY `colors_cow_id_foreign` (`cow_id`);

--
-- Indeksy dla tabeli `cows`
--
ALTER TABLE `cows`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cows_freepik_task_id_unique` (`freepik_task_id`),
  ADD UNIQUE KEY `cows_image_id_unique` (`image_id`),
  ADD KEY `cows_user_id_foreign` (`user_id`),
  ADD KEY `cows_breed_id_foreign` (`breed_id`),
  ADD KEY `cows_ai_model_id_foreign` (`ai_model_id`),
  ADD KEY `cows_engine_id_foreign` (`engine_id`);

--
-- Indeksy dla tabeli `cow_theme`
--
ALTER TABLE `cow_theme`
  ADD PRIMARY KEY (`cow_id`,`theme_id`),
  ADD KEY `cow_theme_theme_id_foreign` (`theme_id`);

--
-- Indeksy dla tabeli `engines`
--
ALTER TABLE `engines`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indeksy dla tabeli `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jobs_queue_index` (`queue`);

--
-- Indeksy dla tabeli `job_batches`
--
ALTER TABLE `job_batches`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `notifications_user_id_foreign` (`user_id`);

--
-- Indeksy dla tabeli `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD PRIMARY KEY (`email`);

--
-- Indeksy dla tabeli `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`),
  ADD KEY `personal_access_tokens_expires_at_index` (`expires_at`);

--
-- Indeksy dla tabeli `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessions_user_id_index` (`user_id`),
  ADD KEY `sessions_last_activity_index` (`last_activity`);

--
-- Indeksy dla tabeli `themes`
--
ALTER TABLE `themes`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ai_models`
--
ALTER TABLE `ai_models`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `breeds`
--
ALTER TABLE `breeds`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `colors`
--
ALTER TABLE `colors`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `cows`
--
ALTER TABLE `cows`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `engines`
--
ALTER TABLE `engines`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `jobs`
--
ALTER TABLE `jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `themes`
--
ALTER TABLE `themes`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `colors`
--
ALTER TABLE `colors`
  ADD CONSTRAINT `colors_cow_id_foreign` FOREIGN KEY (`cow_id`) REFERENCES `cows` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `cows`
--
ALTER TABLE `cows`
  ADD CONSTRAINT `cows_ai_model_id_foreign` FOREIGN KEY (`ai_model_id`) REFERENCES `ai_models` (`id`),
  ADD CONSTRAINT `cows_breed_id_foreign` FOREIGN KEY (`breed_id`) REFERENCES `breeds` (`id`),
  ADD CONSTRAINT `cows_engine_id_foreign` FOREIGN KEY (`engine_id`) REFERENCES `engines` (`id`),
  ADD CONSTRAINT `cows_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `cow_theme`
--
ALTER TABLE `cow_theme`
  ADD CONSTRAINT `cow_theme_cow_id_foreign` FOREIGN KEY (`cow_id`) REFERENCES `cows` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cow_theme_theme_id_foreign` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
