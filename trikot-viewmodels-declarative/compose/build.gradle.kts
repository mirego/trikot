plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
}

group = "com.mirego.trikot"

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
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
        kotlinCompilerExtensionVersion = Versions.JETPACK_COMPOSE_COMPILER
    }
    kotlinOptions {
        jvmTarget = "11"
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

    api("androidx.compose.foundation:foundation:${Versions.JETPACK_COMPOSE_FOUNDATION}")
    api("androidx.compose.material:material:${Versions.JETPACK_COMPOSE_MATERIAL}")
    api("androidx.compose.runtime:runtime:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.ui:ui-tooling:${Versions.JETPACK_COMPOSE_UI_TOOLING}")
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
