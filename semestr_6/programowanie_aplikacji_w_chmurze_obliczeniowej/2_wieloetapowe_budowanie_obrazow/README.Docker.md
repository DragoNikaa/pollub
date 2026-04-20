## Budowa obrazu
Zbudowanie obrazu o nazwie *webapp* z określeniem wersji aplikacji (w przypadku pominięcia zostanie użyta wartość domyślna) na podstawie utworzonego pliku [Dockerfile](Dockerfile):
```
docker build -t webapp --build-arg VERSION=6.6.6 .
```
```
[+] Building 68.3s (19/19) FINISHED                                                                docker:desktop-linux
 => [internal] load build definition from Dockerfile                                                               0.0s
 => => transferring dockerfile: 2.16kB                                                                             0.0s
 => resolve image config for docker-image://docker.io/docker/dockerfile:1                                          1.1s
 => [auth] docker/dockerfile:pull token for registry-1.docker.io                                                   0.0s
 => CACHED docker-image://docker.io/docker/dockerfile:1@sha256:2780b5c3bab67f1f76c781860de469442999ed1a0d7992a5ef  0.0s
 => => resolve docker.io/docker/dockerfile:1@sha256:2780b5c3bab67f1f76c781860de469442999ed1a0d7992a5efdf2cffc0e3d  0.0s
 => [internal] load metadata for docker.io/library/nginx:1.29.8-alpine                                             0.7s
 => [auth] library/nginx:pull token for registry-1.docker.io                                                       0.0s
 => [internal] load .dockerignore                                                                                  0.0s
 => => transferring context: 721B                                                                                  0.0s
 => [internal] load build context                                                                                  0.0s
 => => transferring context: 3.10kB                                                                                0.0s
 => [stage-1 1/6] FROM docker.io/library/nginx:1.29.8-alpine@sha256:5616878291a2eed594aee8db4dade5878cf7edcb475e5  0.0s
 => => resolve docker.io/library/nginx:1.29.8-alpine@sha256:5616878291a2eed594aee8db4dade5878cf7edcb475e59193904b  0.0s
 => [build 1/4] ADD alpine-minirootfs-3.23.3-x86_64.tar /                                                          0.1s
 => CACHED [stage-1 2/6] WORKDIR /usr/app                                                                          0.0s
 => [stage-1 3/6] COPY entrypoint.sh .                                                                             0.0s
 => [stage-1 4/6] COPY nginx.conf /etc/nginx/nginx.conf                                                            0.0s
 => [build 2/4] WORKDIR /usr/app                                                                                   0.0s
 => [build 3/4] COPY web .                                                                                         0.0s
 => [build 4/4] RUN apk add --no-cache openjdk17 gradle &&  gradle clean bootJar                                  54.7s
 => [stage-1 5/6] COPY --from=build /usr/app/build/libs/*.jar webapp.jar                                           0.1s
 => [stage-1 6/6] RUN apk add --no-cache openjdk17-jre &&  chmod +x entrypoint.sh                                  5.3s
 => exporting to image                                                                                             5.5s
 => => exporting layers                                                                                            4.5s
 => => exporting manifest sha256:202f4d2d4234ce1a7e32ae67577721ff116a04179283aaba8b8cd2db8edd9807                  0.0s
 => => exporting config sha256:7c82b192a8fd274b85ee3b9b0e18fd57b786d1e3f26f5676e64d8da2d66447f4                    0.0s
 => => exporting attestation manifest sha256:72012483f06d17050ab7c6f20b849a41cdb66d7838de2d7cb72910e4e2a99827      0.0s
 => => exporting manifest list sha256:f88b1e785b71c1f24ae16306d38e88da34c0547b9509c49f4348a1e3e8fdaa64             0.0s
 => => naming to docker.io/library/webapp:latest                                                                   0.0s
 => => unpacking to docker.io/library/webapp:latest                                                                0.9s
```

## Uruchomienie kontenera
Uruchomienie kontenera o nazwie *webapp-container* na bazie opracowanego obrazu (np. z przekierowaniem portu 8085):
```
docker run -d -p 8085:80 --name webapp-container webapp
```
```
3c7a52a3c2273fc1f02795c8b7356fb314884e778212528d62c1004cfe41331c
```
Strona WWW będzie dostępna pod adresem:<br>
http://localhost:8085
<br><br>
## Weryfikacja działania
Sprawdzenie działania kontenera i poprawnego funkcjonowania opracowanej aplikacji:
```
docker ps --filter name=webapp-container
```
```
CONTAINER ID   IMAGE     COMMAND                  CREATED              STATUS                        PORTS                                     NAMES
3c7a52a3c227   webapp    "./entrypoint.sh ngi…"   About a minute ago   Up About a minute (healthy)   0.0.0.0:8085->80/tcp, [::]:8085->80/tcp   webapp-container
```
Status *healthy* jest wynikiem działania instrukcji [HEALTHCHECK](Dockerfile#L51-L52) i potwierdza, że aplikacja poprawnie odpowiada na zapytania HTTP.

Sprawdzenie, czy aplikacja realizuje wymaganą funkcjonalność:
```
curl localhost:8085
```
```
IP address: 172.17.0.2
Hostname: 3c7a52a3c227
App version: 6.6.6
```
Wynik potwierdza poprawne odczytywanie danych serwera oraz wersji aplikacji określonej podczas budowy obrazu.