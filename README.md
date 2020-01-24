## Usage

### Setting up the project

1. Clone this project (`git clone https://github.com/mirego/trikot.patron.git`)
2. Delete the internal Git directory (`rm -rf .git`)
3. Run the boilerplate setup script (`./boilerplate-setup.sh`)
4. Create a new Git repository (`git init`)
5. Create the initial Git commit (`git commit -a -m "Initial commit"`)

### Building the project

1. Open root folder in Android Studio to run the Android App.
2. In a terminal, cd into `ios` folder and run `bundle exec pod install` to be able to run the iOS App.

## What to Replace

## Root

- ./jobs/build_jobs.groovy
- ./jobs/ios_fastlane.groovy
```
String clientName = 'SAMPLECLIENT'.toLowerCase().replaceAll(' ','_')
String projectName = 'SAMPLEPROJECT'.toLowerCase().replaceAll(' ','_')
String projectGithubPath = 'mirego/SAMPLEREPO'
String slackNotificationChannel = '#SAMPLESLACKCHANNEL'
```

### TODO:

- [ ] Keystores
- [ ] Fastfiles
