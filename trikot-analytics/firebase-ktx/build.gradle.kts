plugins {
    id("com.android.library")
    id("kotlin-platform-android")
    id("org.jlleitschuh.gradle.ktlint")
    id("maven-publish")
    id("mirego.release").version("2.0")
    id("mirego.publish").version("1.0")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.trikot.analytics"

configurations.forEach { it.exclude("org.reactivestreams") }

dependencies {
    api(project(Dependencies.TRIKOT_ANALYTICS))
    api(project(Dependencies.TRIKOT_FOUNDATION))
    api(project(Dependencies.TRIKOT_STREAMS))
    implementation("com.google.firebase:firebase-analytics:19.0.2")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.3.0")
}

android {
    defaultConfig {
        compileSdkVersion(30)
        minSdkVersion(21)
        targetSdkVersion(30)
    }
}

release {
    checkTasks = listOf("check")
    buildTasks = listOf("publish")
    updateVersionPart = 2
    tagPrefix = "firebase-ktx-"
}

tasks {
    val sourcesJar by registering(Jar::class) {
        from(android.sourceSets.getByName("main").java.srcDirs)
        archiveClassifier.set("sources")
    }

    artifacts {
        archives(sourcesJar)
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("firebaseAar") {
                from(components["release"])
                artifactId = "firebase-ktx"
                artifact(tasks["sourcesJar"])
            }
        }
    }
}
