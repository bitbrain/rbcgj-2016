#!/bin/bash

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

echo "Deploying to itch.io.."
wget http://dl.itch.ovh/butler/darwin-amd64/head/butler
chmod +x butler
butler -V
touch butler_creds
echo -n $ITCH_API_KEY > butler_creds

butler login -i butler_creds --assume-yes

# Upload game
butler push game.zip myrealitycoding/the-legend-of-studentenfutter:linux-universal

# Cleanup
echo "Cleanup.."
butler logout -i butler_creds --assume-yes
rm -rf game
rm butler
rm game.zip

echo "Done."
