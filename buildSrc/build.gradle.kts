plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
    implementation("com.android.tools.build:gradle:7.4.0")
}
