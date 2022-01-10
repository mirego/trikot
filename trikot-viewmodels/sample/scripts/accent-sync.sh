#!/usr/bin/env sh

master_language=fr
translated_languages=()
translations_directory='../common/src/commonMain/resources/translations'

if [ -z "${ACCENT_API_KEY}" ];
then
  echo "ACCENT_API_KEY must be set."
  exit
fi

if [ -z "${ACCENT_API_URL}" ];
then
  echo "ACCENT_API_URL must be set."
  exit
fi

export API_KEY=$ACCENT_API_KEY
export API_URL=$ACCENT_API_URL

echo "Master language: ${master_language}"
echo "Translated languages: ${translated_languages[*]}"
echo "Translations directory: ${translations_directory}"

RED='\033[0;31m'
RED_BOLD='\033[1;31m'
GREEN='\033[0;32m'
GREEN_BOLD='\033[1;32m'
YELLOW='\033[0;33m'
NO_COLOR='\033[0m'

error_status=0

run() {
  eval "${@}"
  last_exit_status=${?}

  if [ ${last_exit_status} -ne 0 ]; then
    echo "\n${RED}↳ Something went wrong. Program exited with ${last_exit_status} ✘${NO_COLOR}"
    error_status=${last_exit_status}
  else
    echo "${GREEN}↳ Success ✔${NO_COLOR}"
  fi
}

header() {
  echo "\n\n${YELLOW}▶ $1${NO_COLOR}"
}

sync() {
  for file in $translations_directory/translation.$master_language.json ; do
    echo $file
    accent sync --input-file=$file --language=$master_language --document-path=${file##*/} --document-format=json
  done
}

merge() {
  for language in "${translated_languages[@]}"; do
    for file in $translations_directory/translation.$language/*.json ; do
      echo $file
      accent merge --merge-type=passive --input-file=$file --language=$language --document-path=${file##*/} --document-format=json
    done
  done
}

fetch() {
  for file in $translations_directory/translation.$master_language.json ; do
    echo $file
    accent export --document-path=${file##*/} --document-format=simple_json --language=$master_language --order-by=key-asc > $file
  done

  for language in "${translated_languages[@]}"; do
    for file in $translations_directory/translation.$language.json ; do
      echo $file
      accent export --document-path=${file##*/} --document-format=simple_json --language=$language --order-by=key-asc > $file
    done
  done
}

header "Performing Accent sync…"
run sync

header "Performing Accent merges…"
run merge

header "Fetching Accent files…"
run fetch

if [ ${error_status} -ne 0 ]; then
  echo "\n\n${YELLOW}▶▶ One of the actions ${RED_BOLD}failed${YELLOW}.${NO_COLOR}"
else
  echo "\n\n${YELLOW}▶▶ All actions ${GREEN_BOLD}succeeded${YELLOW}!${NO_COLOR}"
fi

exit $error_status
