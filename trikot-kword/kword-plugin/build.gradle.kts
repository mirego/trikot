plugins {
    id("groovy")
    id("mirego.publish")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.squareup:kotlinpoet:${Versions.KOTLIN_POET}")
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
