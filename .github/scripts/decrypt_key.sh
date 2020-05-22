#!/bin/sh

# Decrypt the file
# --batch to prevent interactive command --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$FIREBASE_PASSPHRASE" --output communitymanager/google-services.json communitymanager/google-services.json.gpg
gpg --quiet --batch --yes --decrypt --passphrase="$PROPERTIES_PASSPHRASE" --output communitymanager/gradle.properties communitymanager/gradle.properties.gpg
