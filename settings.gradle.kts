pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "UiPlayground"

include(":androidApp")
include(":app")
include(":baseline-profile")
include(":benchmark")
include(":components")
include(":core")
include(":components:baseline-profile")
include(":desktopApp")
include(":shaders")
include(":shaders:baseline-profile")
include(":testing")
include(":theme")
include(":uiautomator-fixtures")
include(":webApp")

plugins {
    // Compose Hot Reload
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
