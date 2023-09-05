import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class TagVersionTask : DefaultTask() {
    @TaskAction
    fun tagVersion() {
        try {
            val version = project.property("version")
            "git tag $version".runCommand(workingDir = project.rootDir)
            "git push origin --tags".runCommand(workingDir = project.rootDir)
        } catch (e: Exception) {
            println("Unable to tag: ${e.message}")
        }
    }
}
