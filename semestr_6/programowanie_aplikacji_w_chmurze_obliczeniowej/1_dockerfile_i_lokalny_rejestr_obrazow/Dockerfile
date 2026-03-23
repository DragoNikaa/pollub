# syntax=docker/dockerfile:1

# Użycie konkretnej wersji Ubuntu (zamiast "latest") jako obrazu bazowego w celu zapewnienia stabilności.
# Na moment tworzenia tego Dockerfile jest to najnowsza wersja.
# Użycie instrukcji ARG, aby móc łatwo zmienić wersję systemu.
ARG UBUNTU_VERSION=24.04
FROM ubuntu:${UBUNTU_VERSION}

# Dodanie metadanych informujących o autorze obrazu.
LABEL org.opencontainers.image.authors="Julia Jurczak <s101581@pollub.edu.pl>"

# Zaktualizowanie systemu Ubuntu, zainstalowanie serwera HTTP Apache w najnowszej wersji oraz wyczyszczenie plików tymczasowych.
# Zgrupowanie poleceń w ramach instrukcji RUN w celu utworzenia tylko jednej warstwy i zmniejszenia rozmiaru obrazu.
RUN apt-get update && \
	apt-get install -y --no-install-recommends apache2  && \
	rm -rf /var/lib/apt/lists/*

# Skopiowanie strony WWW do domyślnego katalogu Apache.
COPY index.html /var/www/html/index.html

# Wskazanie portu, na którym nasłuchuje kontener.
EXPOSE 80

# Uruchomienie Apache w trybie pierwszoplanowym, aby kontener nie wyłączył się zaraz po starcie.
CMD ["apache2ctl", "-DFOREGROUND"]