#!/bin/bash

# Why this script ?
# Many dependencies like complete spigot (with nms included) and external lib like authlib and datafixerupper
# are not on maven repository, so we need to download it manually and compile with it

if [[ -d ./tmp ]]; then
  echo "remove existing tmp directory"
  rm -r ./tmp
fi

mkdir ./tmp && cd ./tmp || exit

BASE_URL="https://ci.fabien-hebuterne.fr/common/"

versionsMd5=(
  '1.21.11:spigot-1.21.11-R0.2-SNAPSHOT.jar:a457d82806ffba49cff1333f7409fa64'
  '26.2:spigot-26.2-R0.1-SNAPSHOT.jar:d2118d7d8bfd0d487d1f97f8f9839add'
)

for versionWithMd5 in "${versionsMd5[@]}"; do
  if [[ $versionWithMd5 == *":"* ]]; then
    splitted=(${versionWithMd5//:/ })
    targetFolder=${splitted[0]}
    file=${splitted[1]}
    md5=${splitted[2]}

    if [ "$targetFolder" = "." ]; then
        curl -O "$BASE_URL/$file" --output-dir "$targetFolder"
    else
        mkdir ./"$targetFolder"
        curl -O "$BASE_URL/$targetFolder/$file" --output-dir "./$targetFolder"
    fi

    MD5CHECK=$(md5sum "$targetFolder/$file" | cut -d' ' -f1)

    if [ "$MD5CHECK" = "$md5" ]; then
      echo "Download file $file - OK"
    else
      echo "Download file $file - BAD MD5 - BUILD CANCELED"
      echo "$MD5CHECK"
      exit 1
    fi
  fi
done
# 26.3 : Spigot 26.3 isn't published yet, so nms:v26_3_R1 compiles against Paper's patched server jar
# (Mojang mappings, same NMS classes). The download is a paperclip launcher: patch it once, then keep
# versions/26.3/paper-26.3.jar as tmp/26.3/paper-26.3.jar.
mkdir -p ./26.3
if [[ ! -f ./26.3/paper-26.3.jar ]] || [[ "$(md5sum ./26.3/paper-26.3.jar | cut -d' ' -f1)" != "0b56df36b11458839d6694348d57c68f" ]]; then
  echo "download paper-26.3-177.jar (paperclip)"
  curl -sL -o ./26.3/paperclip-26.3-177.jar "https://fill-data.papermc.io/v1/objects/580c266b6526bb34752e9c2c274be025f763af1ce5b3fa662fa2b3006f966c13/paper-26.3-177.jar"
  (cd ./26.3 && java -Dpaperclip.patchonly=true -jar paperclip-26.3-177.jar && cp versions/26.3/paper-26.3.jar ./paper-26.3.jar)
  echo "paper-26.3.jar - $(md5sum ./26.3/paper-26.3.jar | cut -d' ' -f1)"
fi
