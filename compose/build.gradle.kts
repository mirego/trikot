plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish").version("1.0")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.trikot"

android {
    defaultConfig {
        compileSdkVersion(30)
        minSdkVersion(21)
        targetSdkVersion(30)
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${project.extra["compose_version"]}"
    }
    kotlinOptions {
        jvmTarget = "11"
        useIR = true
    }
}

dependencies {
    implementation(project(":viewmodels-declarative"))
    implementation("com.mirego.trikot:trikotFoundation:${project.extra["trikot_foundation_version"]}")
    implementation("com.mirego.trikot:streams:${project.extra["trikot_streams_version"]}")
    implementation("androidx.compose.foundation:foundation:${project.extra["compose_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${project.extra["kotlin_version"]}")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets.getByName("main").java.srcDirs)
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "viewmodels-declarative-compose"
                artifact(tasks["sourcesJar"])
            }
            create<MavenPublication>("debug") {
                from(components["debug"])
                artifactId = "viewmodels-declarative-compose"
                artifact(tasks["sourcesJar"])
            }
        }
    }
}
