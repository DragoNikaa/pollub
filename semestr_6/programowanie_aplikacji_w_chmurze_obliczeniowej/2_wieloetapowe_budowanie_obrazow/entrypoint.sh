#!/bin/sh

java -jar /usr/app/webapp.jar &

exec "$@"