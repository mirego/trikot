buildscript {
    repositories {
        google()
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.KTLINT}")
    }
}

plugins {
    id("mirego.publish").version("1.0")
    id("mirego.release").version("2.0")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://s3.amazonaws.com/mirego-maven/public")
    }
}

release {
    checkTasks = listOf(
        "check"
    )
    buildTasks = listOf(
        ":trikot-foundation:trikotFoundation:publishToMavenLocal",
        ":trikot-streams:streams:publishToMavenLocal",
        ":trikot-http:http:publishToMavenLocal",
        ":trikot-datasources:datasources:publishToMavenLocal",
        ":trikot-kword:kword:publishToMavenLocal",
        ":trikot-kword:kword-plugin:publishToMavenLocal",
        ":trikot-http:http:publishToMavenLocal",
        ":trikot-viewmodels:viewmodels:publishToMavenLocal",
        ":trikot-viewmodels-declarative:viewmodels-declarative:publishToMavenLocal",
        ":trikot-viewmodels-declarative:compose:publishToMavenLocal",
        ":trikot-viewmodels-declarative:compose:publishToMavenLocal",
        ":trikot-analytics:analytics:publishToMavenLocal",
        ":trikot-analytics:analytics-viewmodel:publishToMavenLocal",
        ":trikot-analytics:firebase-ktx:publishToMavenLocal",
        ":trikot-analytics:mixpanel-ktx:publishToMavenLocal"
    )
    updateVersionPart = 2
}
