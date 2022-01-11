plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.trikot"

android {
    defaultConfig {
        compileSdk = 30
        minSdk = 21
        targetSdk = 30
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
        kotlinCompilerExtensionVersion = Versions.jetpackCompose
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("coil.annotation.ExperimentalCoilApi")
    }
}

dependencies {
    implementation(project(Dependencies.trikotViewModelsDeclarative))
    implementation(project(Dependencies.trikotFoundation))
    implementation(project(Dependencies.trikotStreams))

    api("androidx.compose.foundation:foundation:${Versions.jetpackCompose}")
    api("androidx.compose.material:material:${Versions.jetpackCompose}")
    api("androidx.compose.runtime:runtime:${Versions.jetpackCompose}")
    api("androidx.compose.ui:ui-tooling:${Versions.jetpackCompose}")
    api("io.coil-kt:coil-compose:${Versions.coil}")

    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets.getByName("main").java.srcDirs().toString())
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
