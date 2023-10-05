import org.jetbrains.kotlin.gradle.model.KotlinAndroidExtension

buildscript {
    repositories {
        google()
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    configurations.all {
        resolutionStrategy {
            force("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.7.0")
        }
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
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
}

allprojects {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
        maven("https://s3.amazonaws.com/mirego-maven/public")
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")


    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("0.48.0")
    }
}

/**
 * Kotlin binary compatibility validator configuration
 * https://github.com/Kotlin/binary-compatibility-validator#setup
 */
apiValidation {
    /* Packages that are excluded from public API dumps even if they contain public API. */
    // ignoredPackages.add("kotlinx.coroutines.internal")

    /* Sub-projects that are excluded from API validation */
    ignoredProjects.addAll(listOf("sample", "android", "common", "test-utils"))

    /* Classes (fully qualified) that are excluded from public API dumps even if they contain public API. */
    // ignoredClasses.addAll(listOf("", ""))

    /* Set of annotations that exclude API from being public. */
    // nonPublicMarkers.add("")

    /* Flag to programmatically disable compatibility validator */
    validationDisabled = false
}

tasks {
    val writeDevVersion by registering(WriteProperties::class) {
        destinationFile.set(file("${rootDir}/gradle.properties"))
        properties(java.util.Properties().apply { load(destinationFile.asFile.get().reader()) }.mapKeys { it.key.toString() })
        val gitCommits = "git rev-list --count HEAD".runCommand(workingDir = rootDir)
        val originalVersion = project.version.toString().replace("-dev\\w+".toRegex(), "")
        property("version", "$originalVersion-dev$gitCommits")
    }
    val tagVersion by registering(TagVersionTask::class)
}
