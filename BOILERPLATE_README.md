# What to Replace

## Root

- ./jobs/build_jobs.groovy
- ./jobs/ios_fastlane.groovy
```
String clientName = 'SAMPLECLIENT'.toLowerCase().replaceAll(' ','_')
String projectName = 'SAMPLEPROJECT'.toLowerCase().replaceAll(' ','_')
String projectGithubPath = 'mirego/SAMPLEREPO'
String slackNotificationChannel = '#SAMPLESLACKCHANNEL'
```

# !!ClientName!! - !!ProjectName!!
!!Project description!!

## 🏎 Prerequisite
- !!Latest version!! of [Android Studio](https://developer.android.com/).
- !!Latest version!! of [XCode](https://developer.apple.com/download/).
- Make sure you have ANDROID_HOME environment variable configured
- Ruby gem [Bundler](https://bundler.io/) -> ```gem install bundler```
- Make sure to have run at the root of the project ```git submodule update --init --recursive```

# ☄️ [Core](/core)
Common business logic for `!!ProjectName!!`. See Android section on how to develop / compile the core.

### 🏗 Code and architecture
!!Here we should lay down the specifity and the reason why we built the app that way.!!
!!If needed, put more info in an docs/ARCHITECTURE.MD file!!

### 🚧 Dependencies
!!Where are the dependencies (ex: [`gradle.properties`](gradle.properties)). What are the specific dependencies we use!!

### 🔭 Know todo's and developer notes
|*Priority*|*notes*|
|---|---|
|High|!!...!!|
|Low|!!...!!|

### 🉐 Localization
Localization Strings are located in !![translations.json](/core/src/main/resources/translations.json)!!
They are synchronized on [Accent](https://accent2.mirego.com/) using [Circumflex](https://github.com/mirego/circumflex)

### 🛎 Notifications payload
!!What are the use case of our notifications!!

##### !!Notification type!!
Payload
```
{
    "to": "..."
    "mutable_content": true,
    "notification":{
      "body": "Notification text goes here"
    },
    "data":{
      "xyz": "..."
    },
    "priority":10
  }
```

!!Command line how to test the notification.!!
```
curl https://fcm.googleapis.com/fcm/send -X POST \
   --header "..." \
   --Header "Content-Type: application/json" \
    -d '
    {
      "to": "/topics/android"
      "data":{
        "alert": "..."
      },
      "priority":10
    }'
```

# 🤖 [Android App](/android)

### Supported devices
- !!Phones and Tablets!!
- !!Android 5+!!

### Setup
- `Android Studio > Open > Root Directory of the project` 
- Select a build variant in the `Build Variants` tool window
  - !!Add variant details and what they are for!!
- At this point you should be able to run the application by selecting `Run > Run !!ProjectName!!`

# 🍏 [iOS App](/ios)

### Supported devices
- !!Phones and Tablets!!
- !!iOS 11.3+!!

### Setup
- Open the terminal app
- Go in the `ios` directory of the project
- Run `bundle install` to install bundler dependencies
- Run `bundle exec pod install` to: 
    - Transpile core `java` code to `objective-c`
    - Create a `.podspec` file
    - Install project dependencies (including `core`)
- Open the workspace (`!!ProjectName!!.xcworkspace`)
- Run the project by selecting `Product > Run` 

# 🚀 Environments and deployment
- !!Link to the jenkins job!!
- !!How to deploy on the App Store!!
- !!How to deploy on google play!!

# 🔗 Links
- [Accent](https://accent.mirego.com/projects/**ProjectId**)

- [Analytics - QA](http://!!todo!!)
- [Analytics - Staging](http://!!todo!!)
- [Analytics - Production](http://!!todo!!)

- [Firebase - QA](http://!!todo!!)
- [Firebase - Staging](http://!!todo!!)
- [Firebase - Production](http://!!todo!!)

- [CMS - QA](http://!!todo!!)
- [CMS - Staging](http://!!todo!!)
- [CMS - Production](http://!!todo!!)