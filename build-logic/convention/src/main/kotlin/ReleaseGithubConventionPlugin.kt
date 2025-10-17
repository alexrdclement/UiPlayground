import org.gradle.api.Plugin
import org.gradle.api.Project

class ReleaseGithubConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.shipkit.shipkit-auto-version")
                apply("org.shipkit.shipkit-changelog")
                apply("org.shipkit.shipkit-github-release")
            }

            afterEvaluate {
                tasks.named("generateChangelog") {
                    val task = this as org.shipkit.changelog.GenerateChangelogTask
                    task.previousRevision = target.extensions.extraProperties["shipkit-auto-version.previous-tag"] as String?
                    task.githubToken = System.getenv("GITHUB_TOKEN")
                    task.repository = "alexrdclement/UiPlayground"
                }

                tasks.named("githubRelease") {
                    val task = this as org.shipkit.github.release.GithubReleaseTask
                    dependsOn(tasks.named("generateChangelog"))
                    task.repository = "alexrdclement/UiPlayground"
                    task.changelog = tasks.named("generateChangelog").get().outputs.files.singleFile
                    task.githubToken = System.getenv("GITHUB_TOKEN")
                    task.newTagRevision = System.getenv("GITHUB_SHA")
                }
            }
        }
    }
}
