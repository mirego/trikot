buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
        classpath("com.google.gms:google-services:4.3.8")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")
    }
}

repositories {
    google()
    jcenter()
}

allprojects {
    buildscript {
        repositories {
            google()
        }
    }
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
}
