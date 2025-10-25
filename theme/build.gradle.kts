plugins {
    id(libs.plugins.alexrdclement.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
    id(libs.plugins.alexrdclement.maven.publish.get().pluginId)
}

kotlin {
    libraryTargets(
        androidNamespace = "com.alexrdclement.uiplayground.theme",
        iosFrameworkBaseName = "Theme",
    )

    sourceSets {
        commonMain {
            dependencies {
                api(compose.foundation)
                api(compose.ui)

                implementation(compose.components.uiToolingPreview)

                implementation(projects.shaders)
            }
        }
    }
}
