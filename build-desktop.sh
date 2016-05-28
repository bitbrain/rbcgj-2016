#!/bin/bash

# Gradle build
echo "Build game.."
chmod +x gradlew
./gradlew desktop:dist

echo "Pack game.."
mkdir game
mv desktop/build/libs/desktop-1.0.jar game/legend-of-studentenfutter.jar
zip -r game.zip game/
rm -rf game/

echo "Done."
