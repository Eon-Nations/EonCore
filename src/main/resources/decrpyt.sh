#!/bin/sh
# Script from the Github Docs

mkdir private-key
gpg --quiet --batch --yes --decrpyt --passphrase="$DECRYPTION_PASSWORD" --output /private-key/action-key src/main/action-key.gpg