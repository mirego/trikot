String clientName = 'SAMPLECLIENT'.toLowerCase().replaceAll(' ','_')
String projectName = 'SAMPLEPROJECT'.toLowerCase().replaceAll(' ','_')
String projectGithubPath = 'mirego/SAMPLEREPO'
String folderName = clientName+'-'+projectName
String slackNotificationChannel = '#SAMPLESLACKCHANNEL'
String projectDisplayName = projectName

folder("$folderName") {
    description('Jobs related to ' + clientName)
}

/*********************************************************
*********************************************************

  QA build -> master branch

*********************************************************
**********************************************************/

job("$folderName/$clientName-$projectName-ios-qa") {
    description("Builds $projectDisplayName iOS app")
    logRotator {
        numToKeep(5)
    }
    properties {
        githubProjectUrl("https://github.com/$projectGithubPath" + ".git")
    }
    parameters {
        stringParam {
            name('Branch')
            defaultValue('origin/master')
            description('The git branch to be built')
            trim(true)
        }
        choiceParam('Lane', ['qa'], 'Name of the lane to run in fastlane')
    }
    environmentVariables {
        keepBuildVariables(true)
        keepSystemVariables(true)
        propertiesFile('${HOME}/.build_ios_env')
    }
    scm {
        git {
            branch('${Branch}')
            remote {
                github(projectGithubPath, 'ssh')
                credentials('github')
            }
            extensions {
                submoduleOptions {
                    recursive(true)
                }
                wipeOutWorkspace()
            }
        }
    }
    steps {
        shell('''cd ios
rubyVersion=$(<.ruby-version)
if [ "$(rbenv versions --bare | grep "${rubyVersion}")" == "" ]; then
  echo "ruby version: ${rubyVersion} is not installed"
  rbenv install ${rubyVersion}
  rbenv rehash
  gem install bundler
  rbenv rehash
fi
gem update bundler
bundle install
bundle update fastlane
bundle exec fastlane install_plugins
bundle exec fastlane ${Lane} --verbose''')
    }
    wrappers {
        colorizeOutput()
    }
    publishers {
        slackNotifier {
            commitInfoChoice('NONE')
            notifyBackToNormal(true)
            notifyFailure(true)
            room(slackNotificationChannel)
        }
    }
}


/*********************************************************
*********************************************************

    Staging build -> master branch

*********************************************************
**********************************************************/

job("$folderName/$clientName-$projectName-ios-staging") {
    description("Builds $projectDisplayName iOS app")
    logRotator {
        numToKeep(5)
    }
    properties {
        githubProjectUrl("https://github.com/$projectGithubPath" + ".git")
    }
    parameters {
        stringParam {
            name('Branch')
            defaultValue('origin/master')
            description('The git branch to be built')
            trim(true)
        }
        choiceParam('Lane', ['staging'], 'Name of the lane to run in fastlane')
    }
    environmentVariables {
        keepBuildVariables(true)
        keepSystemVariables(true)
        propertiesFile('${HOME}/.build_ios_env')
    }
    scm {
        git {
            branch('${Branch}')
            remote {
                github(projectGithubPath, 'ssh')
                credentials('github')
            }
            extensions {
                submoduleOptions {
                    recursive(true)
                }
                wipeOutWorkspace()
            }
        }
    }
    steps {
        shell('''cd ios
rubyVersion=$(<.ruby-version)
if [ "$(rbenv versions --bare | grep "${rubyVersion}")" == "" ]; then
  echo "ruby version: ${rubyVersion} is not installed"
  rbenv install ${rubyVersion}
  rbenv rehash
  gem install bundler
  rbenv rehash
fi
gem update bundler
bundle install
bundle update fastlane
bundle exec fastlane install_plugins
bundle exec fastlane ${Lane} --verbose''')
    }
    wrappers {
        colorizeOutput()
    }
    publishers {
        slackNotifier {
            commitInfoChoice('NONE')
            notifyBackToNormal(true)
            notifyFailure(true)
            room(slackNotificationChannel)
        }
    }
}
