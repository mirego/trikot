buildscript {
    repositories {
        google()
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")
    }
}

repositories {
    google()
}

allprojects {
    repositories {
        google()
    }
}
