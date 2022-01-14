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
        ":trikot-foundation:trikotFoundation:publish",
        ":trikot-streams:streams:publish",
        ":trikot-http:http:publish",
        ":trikot-datasources:datasources:publish",
        ":trikot-kword:kword:publish",
        ":trikot-kword:kword-plugin:publish",
        ":trikot-http:http:publish",
        ":trikot-viewmodels:viewmodels:publish",
        ":trikot-viewmodels-declarative:viewmodels-declarative:publish",
        ":trikot-viewmodels-declarative:compose:publish",
        ":trikot-analytics:analytics:publish",
        ":trikot-analytics:analytics-viewmodel:publish",
        ":trikot-analytics:firebase-ktx:publish",
        ":trikot-analytics:mixpanel-ktx:publish"
    )
    updateVersionPart = 2
}
