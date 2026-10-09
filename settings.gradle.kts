pluginManagement {
    val embarrasdfGradlePluginsVersion = file("gradle/libs.versions.toml").readLines()
        .first { it.startsWith("embarrasdf-gradle-plugins") }
        .substringAfter('"').substringBefore('"')
    plugins {
        id("com.embarrasdf.gradle.plugin.settings") version embarrasdfGradlePluginsVersion
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("com.embarrasdf.gradle.plugin.settings")
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
include(":desktopApp")
include(":uiautomator-fixtures")
include(":webApp")

val localPropsFile = rootDir.resolve("local.properties").takeIf { it.exists() }
val localProps = java.util.Properties().apply {
    localPropsFile?.inputStream()?.use { load(it) }
}

val includeLogging = localProps.getProperty("includeLogging")?.toBoolean() ?: false
if (includeLogging && file("../logging").exists()) {
    includeBuild("../logging") {
        dependencySubstitution {
            substitute(module("com.embarrasdf.logging:logger-api")).using(project(":logger-api"))
            substitute(module("com.embarrasdf.logging:logger-impl")).using(project(":logger-impl"))
            substitute(module("com.embarrasdf.logging:loggable")).using(project(":loggable"))
        }
    }
}

val includePalette = localProps.getProperty("includePalette")?.toBoolean() ?: false
if (includePalette && file("../palette").exists()) {
    includeBuild("../palette") {
        dependencySubstitution {
            substitute(module("com.embarrasdf.palette:palette-components")).using(project(":components"))
            substitute(module("com.embarrasdf.palette:palette-modifiers")).using(project(":modifiers"))
            substitute(module("com.embarrasdf.palette:palette-navigation")).using(project(":navigation"))
            substitute(module("com.embarrasdf.palette:palette-theme")).using(project(":theme"))
            substitute(module("com.embarrasdf.palette:palette-theme-components")).using(project(":theme:components"))
        }
    }
}

val includeTrace = localProps.getProperty("includeTrace")?.toBoolean() ?: false
if (includeTrace && file("../trace").exists()) {
    includeBuild("../trace") {
        dependencySubstitution {
            substitute(module("com.embarrasdf.trace:trace")).using(project(":trace"))
        }
    }
}

val includeUievent = localProps.getProperty("includeUievent")?.toBoolean() ?: false
if (includeUievent && file("../uievent").exists()) {
    includeBuild("../uievent") {
        dependencySubstitution {
            substitute(module("com.embarrasdf.uievent:uievent")).using(project(":uievent"))
        }
    }
}
