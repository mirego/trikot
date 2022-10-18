plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
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

tasks {
    val sourcesJar by creating(Jar::class) {
        from(sourceSets.main.get().allSource)
        archiveClassifier.set("sources")
    }

    artifacts {
        archives(sourcesJar)
        archives(jar)
    }
}

publishing {
    publications {
        create<MavenPublication>("binaryAndSources") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}
