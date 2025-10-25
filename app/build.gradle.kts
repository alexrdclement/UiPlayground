plugins {
    id(libs.plugins.alexrdclement.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    libraryTargets(
        androidNamespace = "com.alexrdclement.uiplayground.app",
        iosFrameworkBaseName = "App",
    )

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation.compose)
            implementation(libs.logger.impl)

            implementation(projects.components)
            implementation(projects.shaders)
            implementation(projects.theme)
            implementation(projects.trace)
            implementation(projects.uievent)
        }
        jvmMain {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
        nativeMain {
            dependencies {
                implementation(compose.foundation)
            }
        }
    }
}
