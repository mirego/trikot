plugins {
    id("groovy")
    id("mirego.release").version("2.0")
    id("mirego.publish").version("1.0")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.squareup:kotlinpoet:1.6.0")
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

release {
    tagPrefix = "plugin-"
    checkTasks = listOf("check")
    buildTasks = listOf("publish")
    updateVersionPart = 2
}
