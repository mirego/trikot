@GrabResolver('https://s3.amazonaws.com/mirego-maven/public')
@GrabExclude('org.codehaus.groovy:groovy-all')
@Grab('com.mirego.jenkins:jenkins-jobs:1.2')
import com.mirego.jenkins.jobs.Context

Context context = Context.load(this)
context.standardFolders()

job(context.jobFullName) {
    description("Make a release of trikot.viewmodels")
    logRotator(5)
    parameters {
        stringParam {
            name('Branch')
            defaultValue("${GIT_BRANCH}")
            description('The git branch to be built')
            trim(true)
        }
    }
    scm {
        git {
            branch('${Branch}')
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
        shell('''
          source "${SDKMAN_DIR}/bin/sdkman-init.sh"
          sdk install java 11.0.10.hs-adpt
          sdk use java 11.0.10.hs-adpt
          ./gradlew :viewmodels:release -i -s''')
    }
    publishers {
        slackNotifier {
            notifyFailure(true)
            notifyBackToNormal(true)
            room(context.slackChannel)
        }
    }
}
