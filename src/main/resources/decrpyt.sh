#!/bin/sh
# Script from the Github Docs

mkdir $HOME/private-key
gpg --quiet --batch --yes --decrpyt --passphrase="$DECRYPTION_PASSWORD" --output $HOME/private-key/action-key action-key.gpg