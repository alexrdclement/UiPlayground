pluginManagement {
    // Same check as includeGradlePlugins below; this block can't see other values.
    val includeGradlePlugins = file("local.properties").takeIf { it.exists() }
        ?.let { file -> java.util.Properties().apply { file.inputStream().use { load(it) } } }
        ?.getProperty("includeGradlePlugins")?.toBoolean() == true
    if (includeGradlePlugins && file("../gradle-plugins").exists()) {
        includeBuild("../gradle-plugins")
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// Build against ../gradle-plugins from source only when local.properties sets
// includeGradlePlugins=true. Otherwise this repo uses the published plugins, as
// its CI does, so every build uses the same plugins and shares cached outputs.
val includeGradlePlugins = file("local.properties").takeIf { it.exists() }
    ?.let { file -> java.util.Properties().apply { file.inputStream().use { load(it) } } }
    ?.getProperty("includeGradlePlugins")?.toBoolean() == true

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        if (includeGradlePlugins && file("../gradle-plugins").exists()) {
            create("embarrasdfPluginLibs") {
                from(files("../gradle-plugins/gradle/libs.versions.toml"))
            }
        }
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
