#!/usr/bin/env sh

# -----------------------------------------------------------------------------
# Configuration
# -----------------------------------------------------------------------------

pascalCaseRegex="^[A-Z][a-z]+([A-Z][a-z]+)*$"
packageNameRegex="^([a-z]+\.){2}[a-z]+$"

# The identifiers above will be replaced in the content of the files found below
content=$(find . -type f \( \
  -name "*.xml" -or \
  -name "*.properties" -or \
  -name "*.kt" -or \
  -name "*.swift" -or \
  -name "*.gradle" -or \
  -name "*.podspec" -or \
  -name "*Podfile*" -or \
  -name "*.plist" -or \
  -name "*.pbxproj" \
\) \
  -and ! -path "./boilerplate-setup.sh" \
  -and ! -path "./.idea/*" \
  -and ! -path "./.git/*" \
  -and ! -path "./.gradle/*" \
)

# -----------------------------------------------------------------------------
# Validation
# -----------------------------------------------------------------------------

isPascalCase() {
  if [[ "$1" =~ $pascalCaseRegex ]]; then
    argumentIsPascalCase=true
  else
    echo 'You must specify a name in PascalCase.'
  fi
}

isPackageNameValid() {
  if [[ $1 =~ $packageNameRegex ]]; then
    argumentIsPascalCase=true
  else
    echo 'You must specify a valid package name format (ex: com.example.project)'
  fi
}

# -----------------------------------------------------------------------------
# Helper functions
# -----------------------------------------------------------------------------

header() {
  echo "\033[0;33m▶ $1\033[0m"
}

success() {
  echo "\033[0;32m▶ $1\033[0m"
}

run() {
  echo ${@}
  eval "${@}"
}

# -----------------------------------------------------------------------------
# Execution
# -----------------------------------------------------------------------------

read -p $'\nWhat is your project name?\n' PROJECT_NAME

argumentIsPascalCase=false
while [ ${argumentIsPascalCase} == "false" ]
do
  read -p $'\nWhat will be your iOS framework name? (Should be in PascalCase)\n' USER_INPUT
  isPascalCase "$USER_INPUT"
done
FRAMEWORK_NAME=${USER_INPUT}

argumentIsPascalCase=false
while [ ${argumentIsPascalCase} == "false" ]
do
  read -p $'\nWhat is the package name for this project? (ex: com.example.project)\n' USER_INPUT
  isPackageNameValid "$USER_INPUT"
done
PACKAGE_NAME=${USER_INPUT}

header "Updating project name in project"
run "/usr/bin/sed -i .bak 's/TrikotSample/$PROJECT_NAME/g' settings.gradle"
run "/usr/bin/sed -i .bak 's/Trikot Sample App/$PROJECT_NAME/g' android/src/main/res/values/strings.xml"
run "/usr/bin/sed -i .bak 's/TrikotProjectName/$PROJECT_NAME/g' ios/iosApp/Info.plist"
success "Done!\n"

header "Updating iOS Framework name"
for file in $content; do
  run "/usr/bin/sed -i .bak s/TrikotFrameworkName/$FRAMEWORK_NAME/g $file"
done
mv common/TrikotFrameworkName.podspec "common/${FRAMEWORK_NAME}.podspec"
success "Done!\n"

header "Updating project.pbxproj product bundle identifiers"
run "/usr/bin/sed -i .bak s/com.trikot.project/$PACKAGE_NAME.dev/g ios/iosApp.xcodeproj/project.pbxproj"
run "/usr/bin/sed -i .bak s/com.example.iosAppTests/$PACKAGE_NAME.tests/g ios/iosApp.xcodeproj/project.pbxproj"
run "/usr/bin/sed -i .bak s/com.example.app/$PACKAGE_NAME/g ios/iosApp.xcodeproj/project.pbxproj"
success "Done!\n"

header "Replacing package name in all files"
for file in $content; do
  run "/usr/bin/sed -i .bak s/com.trikot.sample/$PACKAGE_NAME/g $file"
done
success "Done!\n"

header "Renaming folders"
trikotPackage=( com trikot sample )
IFS='.' read -ra SPLITED_PACKAGE_NAME <<< "$PACKAGE_NAME"
iteration=0
for name in "${trikotPackage[@]}"; do
  if [[ "$name" != "${SPLITED_PACKAGE_NAME[${iteration}]}" ]]; then
    for folder in $(find . -type d -name $name -and ! -path '*/generated/*' -and ! -path '*/ios/*' ); do
      run mv $folder ${folder//\/$name/\/${SPLITED_PACKAGE_NAME[${iteration}]}}
    done
  fi
  (( iteration++ ))
done
success "Done!\n"

header "cleaning bak files"
  for file in $(find . -type f -name '*.bak'); do
    run rm $file
  done
success "Done!\n"

header "Importing project README.md and README.fr.md"
run "rm -fr README.md && mv BOILERPLATE_README.md README.md"
success "Done!\n"

header "Removing boilerplate setup script"
run rm -fr boilerplate-setup.sh
success "Done!\n"
