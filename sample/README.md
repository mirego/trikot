# What to Replace

## Root

- settings.gradle
`rootProject.name = 'TrikotSample'`

- gradle.properties
`trikot_framework_name=TrikotFrameworkName`

- ./jobs/build_jobs.groovy
- ./jobs/ios_fastlane.groovy
```
String clientName = 'SAMPLECLIENT'.toLowerCase().replaceAll(' ','_')
String projectName = 'SAMPLEPROJECT'.toLowerCase().replaceAll(' ','_')
String projectGithubPath = 'mirego/SAMPLEREPO'
String slackNotificationChannel = '#SAMPLESLACKCHANNEL'
```

## Common
- `./common/src/commonMain/kotlin/com/trikot/sample`
Rename package

- ./common/build.gradle
`group 'com.trikot.sample'`
`    enumClassName 'com.trikot.sample.localization.KWordTranslation'`

- ./common/TrikotFrameworkName.podspec
`    spec.name                     = 'TrikotFrameworkName'`
`    spec.vendored_frameworks      = "build/bin/ios/TrikotFrameworkName.framework"`
**Rename file to YourFramework.podspec**


## Android
- ./android/build.gradle
`        applicationId "com.trikot.sample"`
`        archivesBaseName = "PatronProjectName-$versionCode"`

- `./android/src/main/java/com/trikot/sample`
Rename packages to match applicationId 

- `./android/src/main/AndroidManifest.xml`
Rename packages to match applicationId 

- `./android/src/main/res/values/strings.xml`
```xml
<string name="app_name">Trikot Sample App</string>
```

- TODO: Keystores

## iOS
- `./ios/Podfile`
```ruby
ENV['TRIKOT_FRAMEWORK_NAME']='TrikotFrameworkName' 
pod 'TrikotFrameworkName', :path => '../common'
```

- `./ios/iosApp/Info.plist`
```
	<key>CFBundleName</key>
	<string>TrikotProjectName</string>
```

- `./ios/iosApp.xcodeproj/project.pbxproj`
```
PRODUCT_BUNDLE_IDENTIFIER = com.trikot.project
PRODUCT_BUNDLE_IDENTIFIER = com.example.iosAppTests
PRODUCT_BUNDLE_IDENTIFIER = com.example.app
```

- All project files
`import TrikotFrameworkName`

- TODO: Fastfiles
