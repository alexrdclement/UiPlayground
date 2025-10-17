import org.gradle.api.Plugin
import org.gradle.api.Project
import org.shipkit.changelog.GenerateChangelogTask
import org.shipkit.github.release.GithubReleaseTask

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
                    with(this as GenerateChangelogTask) {
                        previousRevision = target.extensions.extraProperties["shipkit-auto-version.previous-tag"] as String?
                        githubToken = System.getenv("GITHUB_TOKEN")
                        repository = "alexrdclement/UiPlayground"
                    }
                }

                tasks.named("githubRelease") {
                    with(this as GithubReleaseTask) {
                        dependsOn(tasks.named("generateChangelog"))
                        repository = "alexrdclement/UiPlayground"
                        changelog = tasks.named("generateChangelog").get().outputs.files.singleFile
                        githubToken = System.getenv("GITHUB_TOKEN")
                        newTagRevision = System.getenv("GITHUB_SHA")
                    }
                }
            }
        }
    }
}
