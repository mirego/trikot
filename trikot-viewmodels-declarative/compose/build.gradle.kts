plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
}

group = "com.mirego.trikot"

android {
    namespace = "com.mirego.trikot.vmd.compose"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
    buildFeatures {
        compose = true
        buildConfig = false
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.JETPACK_COMPOSE_COMPILER
    }
    sourceSets.configureEach {
        java.srcDirs("src/$name/kotlin")
    }
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("coil.annotation.ExperimentalCoilApi")
    }
}

dependencies {
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE))
    implementation(project(Project.TRIKOT_FOUNDATION))
    implementation(project(Project.TRIKOT_STREAMS))

    api("androidx.compose.foundation:foundation:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.material:material:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.runtime:runtime:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.ui:ui-tooling:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("io.coil-kt:coil-compose:${Versions.COIL}")

    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(kotlin.sourceSets["main"].kotlin.srcDirs)
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
        }
    }
}
