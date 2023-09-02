plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
    id("kotlin-kapt")
}

group = "com.mirego.trikot"

android {
    namespace = "com.mirego.trikot.viewmodels.android"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }

    sourceSets {
        getByName("test") {
            manifest {
                srcFile("src/test/AndroidManifest.xml")
            }
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(Project.TRIKOT_STREAMS))
    implementation(project(Project.TRIKOT_VIEWMODELS))
    implementation("androidx.lifecycle:lifecycle-process:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.android.material:material:1.3.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.4.1-alpha03")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("org.robolectric:robolectric:4.9.2")
    testImplementation("androidx.fragment:fragment-testing:1.4.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "viewmodels-databinding"
            }
        }
    }
}