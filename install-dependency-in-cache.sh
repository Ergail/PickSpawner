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