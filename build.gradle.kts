buildscript {
    repositories {
        google()
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.4.1")
    }
}

repositories {
    google()
}

allprojects {
    repositories {
        google()
        maven("https://kotlin.bintray.com/kotlinx")
    }
}
