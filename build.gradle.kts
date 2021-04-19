buildscript {
    repositories {
        google()
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${project.extra["kotlin_version"]}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")
    }
}

allprojects {
    repositories {
        google()
    }
}
