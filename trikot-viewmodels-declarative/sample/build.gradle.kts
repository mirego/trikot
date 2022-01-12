buildscript {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://s3.amazonaws.com/mirego-maven/public")
    }
}
