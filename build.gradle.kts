plugins {
    alias(libs.plugins.embarrasdf.github.release)
    alias(libs.plugins.androidx.baselineprofile) apply false
}

githubRelease {
    githubToken = System.getenv("GITHUB_TOKEN")
    repository = "alexrdclement/UiPlayground"
    enabled = !version.toString().endsWith("SNAPSHOT")
    newTagRevision = System.getenv("GITHUB_SHA")
}
