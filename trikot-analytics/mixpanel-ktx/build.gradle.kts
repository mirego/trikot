plugins {
    id("com.android.library")
    id("kotlin-platform-android")
    id("org.jlleitschuh.gradle.ktlint")
    id("maven-publish")
    id("mirego.publish")
}

group = "com.mirego.trikot.analytics"

configurations.forEach { it.exclude("org.reactivestreams") }

dependencies {
    api(project(Project.TRIKOT_ANALYTICS))
    api(project(Project.TRIKOT_FOUNDATION))
    api(project(Project.TRIKOT_STREAMS))
    implementation("com.mixpanel.android:mixpanel-android:5.9.6")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
}

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }
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
            create<MavenPublication>("mixpanelAar") {
                from(components["release"])
                artifactId = "mixpanel-ktx"
                artifact(tasks["sourcesJar"])
            }
        }
    }
}
