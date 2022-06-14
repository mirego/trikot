pluginManagement {
    resolutionStrategy {
        repositories {
            mavenLocal()
            maven("https://plugins.gradle.org/m2/")
            maven("https://s3.amazonaws.com/mirego-maven/public")
            maven("https://jitpack.io")
        }

        eachPlugin {
            if (requested.id.namespace == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:${requested.id.id}:${requested.version}")
            }
            if (requested.id.namespace == "mirego") {
                useModule("mirego:${requested.id.name}-plugin:${requested.version}")
            }
        }
    }
}
rootProject.name = "trikot"

include(":trikot-foundation:trikotFoundation")
include(":trikot-streams:streams")
include(":trikot-streams:test-utils")
include(":trikot-streams:coroutines-interop")
include(":trikot-datasources:datasources")
include(":trikot-http:http")
include(":trikot-analytics:analytics")
include(":trikot-analytics:analytics-viewmodel")
include(":trikot-analytics:firebase-ktx")
include(":trikot-analytics:mixpanel-ktx")
include(":trikot-kword:kword")
include(":trikot-kword:kword-flow")
include(":trikot-kword:kword-streams")
include(":trikot-kword:kword-plugin")
include(":trikot-kword:sample:common")
include(":trikot-kword:sample:android")
include(":trikot-viewmodels:viewmodels")
include(":trikot-viewmodels:sample:common")
include(":trikot-viewmodels:sample:android")
include(":trikot-viewmodels-declarative:viewmodels-declarative")
include(":trikot-viewmodels-declarative:compose")
include(":trikot-viewmodels-declarative:sample:common")
include(":trikot-viewmodels-declarative:sample:android")
include(":trikot-bluetooth:bluetooth")
include(":trikot-graphql:graphql")
