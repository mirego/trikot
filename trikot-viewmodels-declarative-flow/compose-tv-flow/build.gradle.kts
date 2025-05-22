plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "com.mirego.trikot"

android {
    namespace = "com.mirego.trikot.vmd.flow.compose.tv"
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
    sourceSets.configureEach {
        java.srcDirs("src/$name/kotlin")
    }
}

dependencies {
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_FLOW))
    implementation(project(Project.TRIKOT_FOUNDATION))
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_COMPOSE_FLOW))
    api("androidx.compose.foundation:foundation:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.material:material:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.runtime:runtime:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.ui:ui-tooling:${Versions.JETPACK_COMPOSE_RUNTIME}")
    api("androidx.compose.material3:material3:${Versions.JETPACK_COMPOSE_MATERIAL_3}")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    api("io.coil-kt:coil-compose:${Versions.COIL_COMPOSE}")
    implementation("com.google.accompanist:accompanist-drawablepainter:${Versions.ACCOMPANIST}")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}")
    implementation("androidx.tv:tv-foundation:${Versions.JETPACK_COMPOSE_TV}")
    implementation("androidx.tv:tv-material:${Versions.JETPACK_COMPOSE_TV}")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "viewmodels-declarative-compose-tv-flow"
            }
        }
    }
}
