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
        jvmTarget = JavaVersion.VERSION_11.toString()
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
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_FLOW))
    implementation(project(Project.TRIKOT_FOUNDATION))

    api("androidx.compose.foundation:foundation:${Versions.JETPACK_COMPOSE_FOUNDATION}")
    api("androidx.compose.material:material:${Versions.JETPACK_COMPOSE_MATERIAL}")
    api("androidx.compose.runtime:runtime:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.ui:ui-tooling:${Versions.JETPACK_COMPOSE_UI_TOOLING}")
    api("androidx.compose.material3:material3:${Versions.JETPACK_COMPOSE_MATERIAL_3}")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    api("io.coil-kt:coil-compose:${Versions.COIL}")
    implementation("com.google.accompanist:accompanist-drawablepainter:${Versions.ACCOMPANIST}")
    implementation("androidx.appcompat:appcompat:1.5.1")
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
                artifactId = "viewmodels-declarative-compose-flow"
                artifact(tasks["sourcesJar"])
            }
        }
    }
}
