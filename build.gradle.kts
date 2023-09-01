import org.jetbrains.kotlin.gradle.model.KotlinAndroidExtension

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

tasks {
    val writeDevVersion by registering(WriteProperties::class) {
        destinationFile.set(file("${rootDir}/gradle.properties"))
        properties(java.util.Properties().apply { load(destinationFile.asFile.get().reader()) }.mapKeys { it.key.toString() })
        val gitCommits = "git rev-list --count HEAD".runCommand(workingDir = rootDir)
        val originalVersion = project.version.toString().replace("-dev\\w+".toRegex(), "")
        property("version", "$originalVersion-dev$gitCommits")
    }
    val tagVersion by registering {
        try {
            val version = project.property("version")
            "git tag $version".runCommand(workingDir = rootDir)
            "git push origin --tags".runCommand(workingDir = rootDir)
        } catch (e: Exception) {
            println("Unable to tag: ${e.message}")
        }
    }
}
