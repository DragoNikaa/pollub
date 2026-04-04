# syntax=docker/dockerfile:1

# Pierwszy etap budowy – wykorzystanie obrazu bazowego scratch.
FROM scratch AS build

# Dodanie minimalnego systemu operacyjnego (dla architektury x86_64).
ADD alpine-minirootfs-3.23.3-x86_64.tar /

# Ustawienie katalogu roboczego.
WORKDIR /usr/app

# Skopiowanie skryptu budującego aplikację webową do wewnątrz obrazu.
COPY build_webapp.sh .

# Nadanie skryptowi uprawnień do wykonywania. 
RUN chmod +x build_webapp.sh

# Drugi etap budowy – wykorzystanie obrazu bazowego nginx w wersji 1.29.7 (zawierającego pakiet curl).
FROM nginx:1.29.7

# Dodanie metadanych informujących o autorze obrazu.
LABEL org.opencontainers.image.authors="Julia Jurczak <s101581@pollub.edu.pl>"

# Zdefiniowanie zmiennej umożliwiającej przekazanie wersji aplikacji podczas budowy obrazu.
ARG VERSION

# Ustawienie zmiennej środowiskowej na wartość powyższego ARG lub wartość domyślną.
ENV VERSION=${VERSION:-1.0.0}

# Ustawienie katalogu roboczego.
WORKDIR /usr/app

# Skopiowanie skryptu budującego aplikację z pierwszego etapu.
COPY --from=build /usr/app/build_webapp.sh .

# Wskazanie portu, na którym nasłuchuje kontener.
EXPOSE 80

# Zdefiniowanie mechanizmu sprawdzania poprawności działania uruchomionej aplikacji.
HEALTHCHECK --interval=10s --timeout=1s \
	CMD curl -f http://localhost:80/ || exit 1

# Uruchomienie powłoki w celu:
# 1) wykonania skryptu z parametrem określającym lokalizację pliku wynikowego,
# 2) uruchomienia nginx w trybie pierwszoplanowym, aby kontener nie wyłączył się zaraz po starcie.
# Skrypt jest wykonywany w instrukcji CMD, aby pobrać aktualny adres IP i nazwę serwera w momencie uruchomienia kontenera.
# Wykonanie w instrukcji RUN spowodowałoby zapisanie danych z etapu budowy obrazu (nieodzwierciedlających stanu działającego kontenera).
CMD ["/bin/sh", "-c", "./build_webapp.sh '/usr/share/nginx/html/index.html' && nginx -g 'daemon off;'"]