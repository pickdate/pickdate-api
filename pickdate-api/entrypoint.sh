#!/bin/sh
set -eu

MASTER_KEY_FILE="${APP_MASTER_KEY_FILE:-/run/app-secrets/master.key}"

mkdir -p "$(dirname "$MASTER_KEY_FILE")"

if [ ! -f "$MASTER_KEY_FILE" ]; then
  echo "Master key not found, generating..."
  umask 077
  openssl rand -base64 32 > "$MASTER_KEY_FILE"
fi

exec java -jar /app/app.jar
