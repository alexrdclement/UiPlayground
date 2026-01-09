plugins {
    id(libs.plugins.alexrdclement.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.alexrdclement.kotlin.serialization.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
}

kotlin {
    libraryTargets(
        androidNamespace = "com.alexrdclement.uiplayground.app",
        iosFrameworkBaseName = "App",
    )

    sourceSets {
        val nonAndroidMain by creating {
            dependsOn(commonMain.get())
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation.compose)
            implementation(libs.logger.impl)
            implementation(libs.palette.components)
            implementation(libs.palette.modifiers)
            implementation(libs.palette.theme)
            implementation(libs.trace)
            implementation(libs.uievent)
        }
        jvmMain {
            dependsOn(nonAndroidMain)
            dependencies {
                implementation(compose.desktop.common)
            }
        }
        iosMain {
            dependsOn(nonAndroidMain)
        }
        wasmJsMain {
            dependsOn(nonAndroidMain)
        }
    }
}
