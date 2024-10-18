pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "UiPlayground"

include(":androidApp")
include(":baseline-profile")
include(":benchmark")
include(":components")
include(":components:baseline-profile")
include(":shaders")
include(":shaders:baseline-profile")
include(":testing")
include(":theme")
include(":uiautomator-fixtures")
include(":trace")
