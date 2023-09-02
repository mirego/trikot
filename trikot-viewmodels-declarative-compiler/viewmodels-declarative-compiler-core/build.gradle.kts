plugins {
    kotlin("jvm")
    id("mirego.publish")
}

group = "com.mirego.trikot"

repositories {
    mavenCentral()
}

dependencies {
    api("com.google.devtools.ksp:symbol-processing-api:${Versions.KSP}")
    api("com.squareup:kotlinpoet-ksp:1.12.0")
    api(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_ANNOTATIONS))
}

publishing {
    publications {
        create<MavenPublication>("binaryAndSources") {
            from(components["java"])
        }
    }
}
