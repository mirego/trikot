@GrabResolver('https://s3.amazonaws.com/mirego-maven/public')
@GrabExclude('org.codehaus.groovy:groovy-all')
@Grab('com.mirego.jenkins:jenkins-jobs:1.2')
import com.mirego.jenkins.jobs.Context

Context context = Context.load(this)
context.standardFolders()

job("$context.jobFullName-analytics") {
    description("Make a release of trikot.analytics")
    logRotator(5)
    scm {
        git {
            branch("${GIT_BRANCH}")
            remote {
                name('origin')
                url("${GIT_URL}")
                credentials('github')
            }
        }
    }
    wrappers {
        credentialsBinding {
            amazonWebServicesCredentialsBinding {
                accessKeyVariable('MAVEN_AWS_KEY')
                secretKeyVariable('MAVEN_AWS_SECRET')
                credentialsId('mirego-maven-aws')
            }
        }
    }
    steps {
        gradle {
            useWrapper()
            makeExecutable()
            tasks(':analytics:release')
            switches('-i -s')
        }
    }
    publishers {
        slackNotifier {
            notifyFailure(true)
            notifyBackToNormal(true)
            room(context.slackChannel)
        }
    }
}

job("$context.jobFullName-analytics-firebase-ktx") {
    description("Make a release of trikot.analytics.firebase-ktx")
    logRotator(5)
    scm {
        git {
            branch("${GIT_BRANCH}")
            remote {
                name('origin')
                url("${GIT_URL}")
                credentials('github')
            }
        }
    }
    wrappers {
        credentialsBinding {
            amazonWebServicesCredentialsBinding {
                accessKeyVariable('MAVEN_AWS_KEY')
                secretKeyVariable('MAVEN_AWS_SECRET')
                credentialsId('mirego-maven-aws')
            }
        }
    }
    steps {
        gradle {
            useWrapper()
            makeExecutable()
            tasks(':firebase-ktx:release')
            switches('-i -s')
        }
    }
    publishers {
        slackNotifier {
            notifyFailure(true)
            notifyBackToNormal(true)
            room(context.slackChannel)
        }
    }
}

job("$context.jobFullName-analytics-mixpanel-ktx") {
    description("Make a release of trikot.analytics.mixpanel-ktx")
    logRotator(5)
    scm {
        git {
            branch("${GIT_BRANCH}")
            remote {
                name('origin')
                url("${GIT_URL}")
                credentials('github')
            }
        }
    }
    wrappers {
        credentialsBinding {
            amazonWebServicesCredentialsBinding {
                accessKeyVariable('MAVEN_AWS_KEY')
                secretKeyVariable('MAVEN_AWS_SECRET')
                credentialsId('mirego-maven-aws')
            }
        }
    }
    steps {
        gradle {
            useWrapper()
            makeExecutable()
            tasks(':mixpanel-ktx:release')
            switches('-i -s')
        }
    }
    publishers {
        slackNotifier {
            notifyFailure(true)
            notifyBackToNormal(true)
            room(context.slackChannel)
        }
    }
}
