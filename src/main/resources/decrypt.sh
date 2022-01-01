#!/bin/sh
# Script from the Github Docs

mkdir private-key
gpg --quiet --batch --yes --decrypt --passphrase="$DECRYPTION_PASSWORD" --output /private-key/action-key src/main/resources/action-key.gpg