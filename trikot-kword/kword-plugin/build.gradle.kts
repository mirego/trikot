plugins {
    kotlin("jvm")
    id("mirego.publish")
}

dependencies {
    implementation(gradleApi())
    implementation("com.squareup:kotlinpoet:${Versions.KOTLIN_POET}")
}

publishing {
    publications {
        create<MavenPublication>("binaryAndSources") {
            from(components["java"])
        }
    }
}
