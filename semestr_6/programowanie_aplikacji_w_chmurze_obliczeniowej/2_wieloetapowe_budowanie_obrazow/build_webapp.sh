#!/bin/sh

FILE=${1:-index.html}

cat <<EOF > $FILE
IP address: $(hostname -i)
Hostname: $(hostname)
App version: $VERSION
EOF