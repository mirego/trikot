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
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE))
    implementation(project(Project.TRIKOT_STREAMS))
}

publishing {
    publications {
        create<MavenPublication>("binaryAndSources") {
            from(components["java"])
        }
    }
}
