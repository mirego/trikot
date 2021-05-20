import org.gradle.api.Project

val Project.Versions: Versions
    get() = Versions(this)

val Project.Dependencies: Dependencies
    get() = Dependencies(this)

class Versions(project: Project) {

    val kotlin = "1.4.32"
    val trikotStreams = project.property("trikot_streams_version")
    val trikotFoundation = "2.0.2"

    val androidGradlePlugin = "7.0.0-beta01"
    val jetpackCompose = "1.0.0-beta07"
    val googleAccompanist = "0.10.0"
}

class Dependencies(project: Project) {
    val trikotFoundation = "com.mirego.trikot:trikotFoundation:${project.Versions.trikotFoundation}"
    val trikotStreams = "com.mirego.trikot:streams:${project.Versions.trikotStreams}"
}
