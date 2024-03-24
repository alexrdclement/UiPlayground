pluginManagement {
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
include(":app")
include(":baselineprofile")
include(":benchmark")
include(":components")
include(":shaders")
include(":testing")
include(":uiautomator-fixtures")
