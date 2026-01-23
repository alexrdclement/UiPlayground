pluginManagement {
    if (file("../gradle-plugins").exists()) {
        includeBuild("../gradle-plugins")
    }
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
    versionCatalogs {
        if (file("../gradle-plugins").exists()) {
            create("alexrdclementPluginLibs") {
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
            substitute(module("com.alexrdclement.logging:logger-api")).using(project(":logger-api"))
            substitute(module("com.alexrdclement.logging:logger-impl")).using(project(":logger-impl"))
            substitute(module("com.alexrdclement.logging:loggable")).using(project(":loggable"))
        }
    }
}

val includePalette = localProps.getProperty("includePalette")?.toBoolean() ?: false
if (includePalette && file("../palette").exists()) {
    includeBuild("../palette") {
        dependencySubstitution {
            substitute(module("com.alexrdclement.palette:palette-components")).using(project(":components"))
            substitute(module("com.alexrdclement.palette:palette-modifiers")).using(project(":modifiers"))
            substitute(module("com.alexrdclement.palette:palette-theme")).using(project(":theme"))
        }
    }
}

val includeTrace = localProps.getProperty("includeTrace")?.toBoolean() ?: false
if (includeTrace && file("../trace").exists()) {
    includeBuild("../trace") {
        dependencySubstitution {
            substitute(module("com.alexrdclement.trace:trace")).using(project(":trace"))
        }
    }
}

val includeUievent = localProps.getProperty("includeUievent")?.toBoolean() ?: false
if (includeUievent && file("../uievent").exists()) {
    includeBuild("../uievent") {
        dependencySubstitution {
            substitute(module("com.alexrdclement.uievent:uievent")).using(project(":uievent"))
        }
    }
}
