plugins {
    kotlin("jvm")
    id("mirego.publish")
}

group = "com.mirego.trikot"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_COMPILER_CORE))
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_FLOW))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")

    testImplementation("dev.zacsweers.kctfork:ksp:${Versions.KOTLIN_COMPILE_TESTING}")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}")
}

publishing {
    publications {
        create<MavenPublication>("binaryAndSources") {
            from(components["java"])
        }
    }
}
