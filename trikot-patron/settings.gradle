pluginManagement {
    resolutionStrategy {
        repositories {
            maven {
                url 'https://plugins.gradle.org/m2/'
            }
            maven {
                url('https://s3.amazonaws.com/mirego-maven/public')
            }
            jcenter()
            maven { url 'https://jitpack.io' }
        }
        eachPlugin {
            if (requested.id.id in ['kotlin-multiplatform', 'kotlinx-serialization']) {
                useModule("org.jetbrains.kotlin:${requested.id.id}:${requested.version}")
            }
            if (requested.id.namespace == 'mirego') {
                useModule "mirego:${requested.id.name}-plugin:${requested.version}"
            }
        }
    }
}
rootProject.name = 'TrikotSample'

include ':android', 'common'
