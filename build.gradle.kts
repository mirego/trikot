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
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:${Versions.ATOMIC_FU}")
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
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven("https://s3.amazonaws.com/mirego-maven/public")
    }
}

release {
    checkTasks = listOf(
        "check"
    )
    buildTasks = listOf(
        ":trikot-foundation:trikotFoundation:publish",
        ":trikot-foundation:test-utils:publish",
        ":trikot-streams:streams:publish",
        ":trikot-streams:test-utils:publish",
        ":trikot-streams:coroutines-interop:publish",
        ":trikot-http:http:publish",
        ":trikot-datasources:datasources-core:publish",
        ":trikot-datasources:datasources-flow:publish",
        ":trikot-datasources:datasources-streams:publish",
        ":trikot-kword:kword:publish",
        ":trikot-kword:kword-streams:publish",
        ":trikot-kword:kword-flow:publish",
        ":trikot-kword:kword-plugin:publish",
        ":trikot-http:http:publish",
        ":trikot-viewmodels:viewmodels:publish",
        ":trikot-viewmodels-declarative:viewmodels-declarative:publish",
        ":trikot-viewmodels-declarative:compose:publish",
        ":trikot-viewmodels-declarative-flow:viewmodels-declarative-flow:publish",
        ":trikot-viewmodels-declarative-flow:compose-flow:publish",
        ":trikot-viewmodels-declarative-annotations:publish",
        ":trikot-viewmodels-declarative-compiler:viewmodels-declarative-compiler-streams:publish",
        ":trikot-viewmodels-declarative-compiler:viewmodels-declarative-compiler-flow:publish",
        ":trikot-analytics:analytics:publish",
        ":trikot-analytics:analytics-viewmodel:publish",
        ":trikot-analytics:firebase-ktx:publish",
        ":trikot-analytics:mixpanel-ktx:publish",
        ":trikot-bluetooth:bluetooth:publish",
        ":trikot-graphql:graphql:publish"
    )
    updateVersionPart = 2
}

tasks {
    val writeDevVersion by registering(WriteProperties::class) {
        outputFile = file("${rootDir}/gradle.properties")
        properties(java.util.Properties().apply { load(outputFile.reader()) }.mapKeys { it.key.toString() })
        val gitCommits = "git rev-list --count HEAD".runCommand(workingDir = rootDir)
        val originalVersion = project.version.toString().replace("-dev\\w+".toRegex(), "")
        property("version", "$originalVersion-dev$gitCommits")
    }
}

// Node.js 16.0.0 is needed on Apple Silicon
// This bug will be fixed in Kotlin 1.6.20
// https://youtrack.jetbrains.com/issue/KT-49109
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}
