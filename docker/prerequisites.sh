#!/usr/bin/env bash

set -e

cd "$(dirname "$0")"
source ./aem.env

function done_message() {
  printf 'done.\n'
}

function download_file() {
  local directory=$1
  local filename=$2

  printf 'Downloading %s/%s to ./%s/%s...' "$BITBUCKET_DOWNLOAD_URL" "$filename" "$directory" "$filename"
  (cd "$directory" && curl -sLu "$BITBUCKET_USERNAME:$BITBUCKET_PASSWORD" "$BITBUCKET_DOWNLOAD_URL/$filename" >"$filename")
  done_message
}

printf 'Cleaning and preparing directories...'
rm -rf downloads
mkdir -p downloads/packages
done_message

download_file "downloads" "AEM_Quickstart.jar"
download_file "downloads" "license.properties"

prefix=10
while IFS= read -r line || [ "$line" ]; do
  download_file "downloads/packages" "$line"
  printf 'Renaming package to %s-%s...' $prefix "$line"
  mv "downloads/packages/$line" "downloads/packages/$prefix-$line"
  done_message
  prefix=$((prefix + 10))
done <'packages.txt'

printf 'Preparations complete.\n'
