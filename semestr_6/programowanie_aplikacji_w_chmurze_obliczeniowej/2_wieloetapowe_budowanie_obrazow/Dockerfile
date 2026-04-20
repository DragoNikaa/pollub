# syntax=docker/dockerfile:1

# Pierwszy etap budowy – wykorzystanie obrazu bazowego scratch.
FROM scratch AS build

# Dodanie minimalnego systemu operacyjnego (dla architektury x86_64).
ADD alpine-minirootfs-3.23.3-x86_64.tar /

# Ustawienie katalogu roboczego.
WORKDIR /usr/app

# Skopiowanie aplikacji webowej (Spring Boot) do wewnątrz obrazu.
COPY web .

# Zainstalowanie niezbędnych pakietów oraz zbudowanie powyższej aplikacji.
RUN apk add --no-cache openjdk17 gradle && \
	gradle clean bootJar

# Drugi etap budowy – wykorzystanie obrazu bazowego nginx w wersji 1.29.8 Alpine (zawierającego pakiet curl).
FROM nginx:1.29.8-alpine

# Dodanie metadanych informujących o autorze obrazu.
LABEL org.opencontainers.image.authors="Julia Jurczak <s101581@pollub.edu.pl>"

# Zdefiniowanie zmiennej umożliwiającej przekazanie wersji aplikacji podczas budowy obrazu.
ARG VERSION

# Ustawienie zmiennej środowiskowej na wartość powyższego ARG lub wartość domyślną.
ENV VERSION=${VERSION:-1.0.0}

# Ustawienie katalogu roboczego.
WORKDIR /usr/app

# Skopiowanie skryptu startowego do wewnątrz obrazu.
COPY entrypoint.sh .

# Skopiowanie pliku z konfiguracją serwera nginx (reverse proxy do aplikacji Spring Boot).
COPY nginx.conf /etc/nginx/nginx.conf

# Skopiowanie aplikacji zbudowanej w pierwszym etapie.
COPY --from=build /usr/app/build/libs/*.jar webapp.jar

# Zainstalowanie pakietu niezbędnego do uruchomienia aplikacji oraz nadanie skryptowi uprawnień do wykonywania.
RUN apk add --no-cache openjdk17-jre && \
	chmod +x entrypoint.sh

# Wskazanie portu, na którym nasłuchuje kontener.
EXPOSE 80

# Zdefiniowanie mechanizmu sprawdzania poprawności działania uruchomionej aplikacji.
HEALTHCHECK --interval=10s --timeout=1s \
	CMD curl -f http://localhost:80/ || exit 1

# Wykonanie skryptu uruchamiającego:
# 1) aplikację Spring Boot w tle,
# 2) serwer nginx w trybie pierwszoplanowym jako proces główny PID 1 (domyślne polecenie przekazane do ENTRYPOINT za pomocą CMD).
ENTRYPOINT ["./entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]