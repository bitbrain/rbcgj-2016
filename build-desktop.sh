#!/bin/bash

# Gradle build
echo "Build game.."
chmod +x gradlew
./gradlew desktop:dist

echo "Pack game.."
mkdir game
mv desktop/build/libs/desktop-1.0.jar game/legend-of-studentenfutter.jar
cp -rf android/assets/fonts/ game/fonts
cp -rf android/assets/i18n/ game/i18n
cp -rf android/assets/maps/ game/maps
cp -rf android/assets/music/ game/music
cp -rf android/assets/sound/ game/sound
cp -rf android/assets/textures game/textures
zip -r game.zip game
rm -rf game/

echo "Done."
