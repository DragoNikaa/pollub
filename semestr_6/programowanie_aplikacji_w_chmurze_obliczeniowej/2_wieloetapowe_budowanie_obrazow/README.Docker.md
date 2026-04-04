## Budowa obrazu
Zbudowanie obrazu o nazwie *webapp* z określeniem wersji aplikacji (w przypadku pominięcia zostanie użyta wartość domyślna) na podstawie utworzonego pliku [Dockerfile](Dockerfile):
```
docker build -t webapp --build-arg VERSION=6.6.6 .
```
```
[+] Building 1.2s (14/14) FINISHED                                                                 docker:desktop-linux
 => [internal] load build definition from Dockerfile                                                               0.0s
 => => transferring dockerfile: 2.02kB                                                                             0.0s
 => resolve image config for docker-image://docker.io/docker/dockerfile:1                                          0.3s
 => CACHED docker-image://docker.io/docker/dockerfile:1@sha256:4a43a54dd1fedceb30ba47e76cfcf2b47304f4161c0caeac2d  0.0s
 => => resolve docker.io/docker/dockerfile:1@sha256:4a43a54dd1fedceb30ba47e76cfcf2b47304f4161c0caeac2db1c61804ea3  0.0s
 => [internal] load metadata for docker.io/library/nginx:1.29.7                                                    0.2s
 => [internal] load .dockerignore                                                                                  0.0s
 => => transferring context: 687B                                                                                  0.0s
 => [internal] load build context                                                                                  0.0s
 => => transferring context: 93B                                                                                   0.0s
 => [stage-1 1/3] FROM docker.io/library/nginx:1.29.7@sha256:7150b3a39203cb5bee612ff4a9d18774f8c7caf6399d6e8985e9  0.0s
 => => resolve docker.io/library/nginx:1.29.7@sha256:7150b3a39203cb5bee612ff4a9d18774f8c7caf6399d6e8985e97e28eb75  0.0s
 => [build 1/4] ADD alpine-minirootfs-3.23.3-x86_64.tar /                                                          0.0s
 => CACHED [stage-1 2/3] WORKDIR /usr/app                                                                          0.0s
 => [build 2/4] WORKDIR /usr/app                                                                                   0.0s
 => [build 3/4] COPY build_webapp.sh .                                                                             0.0s
 => [build 4/4] RUN chmod +x build_webapp.sh                                                                       0.2s
 => [stage-1 3/3] COPY --from=build /usr/app/build_webapp.sh .                                                     0.1s
 => exporting to image                                                                                             0.2s
 => => exporting layers                                                                                            0.1s
 => => exporting manifest sha256:da111589ba90eb00cc83207a6fb7547fdea4d13d644e3dcbd9782ef99a8036ad                  0.0s
 => => exporting config sha256:e5ac5a25c4b6be46bff48901cced76518edba6751e8defa09f90b0ea67005fd1                    0.0s
 => => exporting attestation manifest sha256:bd1522fc9b072c7f8d76b38a78df1c5d6cfa710357b6a196cacb08b93910dcb0      0.0s
 => => exporting manifest list sha256:50f2f0d6d676cdfc3b016fb0c7d4fbb9a648a923ea2d3f7b56561473122445df             0.0s
 => => naming to docker.io/library/webapp:latest                                                                   0.0s
 => => unpacking to docker.io/library/webapp:latest                                                                0.0s
```

## Uruchomienie kontenera
Uruchomienie kontenera o nazwie *webapp-container* na bazie opracowanego obrazu (np. z przekierowaniem portu 8080):
```
docker run -d -p 8080:80 --name webapp-container webapp
```
```
fdf1b22b25ce2807f1459ace4bf6e37101d240a97ef3a3cee9bf759fe1742e3b
```
Strona WWW będzie dostępna pod adresem:<br>
[http://localhost:8080](http://localhost:8080)
<br><br>
## Weryfikacja działania
Sprawdzenie działania kontenera i poprawnego funkcjonowania opracowanej aplikacji:
```
docker ps --filter name=webapp-container
```
```
CONTAINER ID   IMAGE     COMMAND                  CREATED          STATUS                    PORTS                                     NAMES
fdf1b22b25ce   webapp    "/docker-entrypoint.…"   41 seconds ago   Up 40 seconds (healthy)   0.0.0.0:8080->80/tcp, [::]:8080->80/tcp   webapp-container
```
Status *healthy* jest wynikiem działania instrukcji [HEALTHCHECK](Dockerfile#L40-L41) i potwierdza, że aplikacja poprawnie odpowiada na zapytania HTTP.

Sprawdzenie, czy aplikacja realizuje wymaganą funkcjonalność:
```
curl localhost:8080
```
```
IP address: 172.17.0.2
Hostname: fdf1b22b25ce
App version: 6.6.6
```
Wynik potwierdza poprawne odczytywanie danych serwera oraz wersji aplikacji określonej podczas budowy obrazu.