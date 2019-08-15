//
// You'll need to setup a Jenkins Seed Jobs to load the jobs below
// in Jenkins. See: https://github.com/mirego/jenkins-jobs

String clientName = 'TV5'.toLowerCase().replaceAll(' ','_')
String projectName = 'TV5Unis'.toLowerCase().replaceAll(' ','_')
String projectGithubPath = 'mirego/tv5-asterix-multiplatform'
String folderName = clientName+'-'+projectName
String slackNotificationChannel = '#asterix'

// Create folder that will contains all the jobs
folder("$folderName") {
  description('Jobs related to '+clientName+' '+projectName)
}

// Documentation on available DSL methods
// https://jenkins.mirego.com/plugin/job-dsl/api-viewer/index.html
// (It can take a while to load, be patient)

//-----------------------------------------------------------------------------
// Jobs
//-----------------------------------------------------------------------------
job("$folderName/$projectName-android-qa") {
  description('Android QA Build')
  logRotator(5)
  concurrentBuild()
  scm {
    git {
      branch('*/master')
      remote {
        github(projectGithubPath, 'ssh')
        credentials('github')
      }
      extensions {
        submoduleOptions {
          recursive()
        }
        userExclusion {
          excludedUsers('''jenkins
Jenkins
jenkins@mirego.com
mirego-builds
''')
        }
      }
    }
  }
  triggers {
    //scm('@midnight')
  }
  steps {
    gradle {
        useWrapper()
        makeExecutable()
        tasks('clean assembleQA')
        //switches('-S -PincrementVersion=true')
    }
    //shell('''#!/bin/sh
//${TOOLBOX_DIR}/git-push-changes "origin/master"
//''')
  }
  publishers {
          hockeyappRecorder {
            applications {
                hockeyappApplication {
                    apiToken('')
                    appId('8912fb78af2c4e3da0182afc37d19ab0')
                    dsymPath('')
                    libsPath('')
                    uploadMethod {
                        versionCreation {
                            appId('8912fb78af2c4e3da0182afc37d19ab0')
                        }
                    }
                    filePath('android/build/outputs/apk/QA/TV5-*-QA.apk')
                    releaseNotesMethod {
                        changelogReleaseNotes()
                    }
                    tags('')
                    teams('')
                    mandatory(false)
                    notifyTeam(false)
                    downloadAllowed(true)
                    oldVersionHolder {
                        numberOldVersions('')
                        sortOldVersions('')
                        strategyOldVersions('')
                    }
                }
            }
            debugMode(false)
            failGracefully(false)
        }
    slackNotifier {
      notifyFailure(false)
      notifyBackToNormal(false)
      room(slackNotificationChannel)
    }
  }
}

job("$folderName/$projectName-android-staging") {
  description('Android Staging Build')
  logRotator(5)
  concurrentBuild()
  scm {
    git {
      branch('*/master')
      remote {
        github(projectGithubPath, 'ssh')
        credentials('github')
      }
      extensions {
        submoduleOptions {
          recursive()
        }
        userExclusion {
          excludedUsers('''jenkins
Jenkins
jenkins@mirego.com
mirego-builds
''')
        }
      }
    }
  }
  triggers {
    //scm('@midnight')
  }
  steps {
    gradle {
        useWrapper()
        makeExecutable()
        tasks('clean assembleStaging')
        //switches('-S -PincrementVersion=true')
    }
    //shell('''#!/bin/sh
//${TOOLBOX_DIR}/git-push-changes "origin/master"
//''')
  }
  publishers {
          hockeyappRecorder {
            applications {
                hockeyappApplication {
                    apiToken('')
                    appId('1fccc844d3b04b57b81a49f9dd5ed059')
                    dsymPath('')
                    libsPath('')
                    uploadMethod {
                        versionCreation {
                            appId('1fccc844d3b04b57b81a49f9dd5ed059')
                        }
                    }
                    filePath('android/build/outputs/apk/staging/TV5-*-staging.apk')
                    releaseNotesMethod {
                        changelogReleaseNotes()
                    }
                    tags('')
                    teams('')
                    mandatory(false)
                    notifyTeam(false)
                    downloadAllowed(true)
                    oldVersionHolder {
                        numberOldVersions('')
                        sortOldVersions('')
                        strategyOldVersions('')
                    }
                }
            }
            debugMode(false)
            failGracefully(false)
        }
    slackNotifier {
      notifyFailure(false)
      notifyBackToNormal(false)
      room(slackNotificationChannel)
    }
  }
}
