plugins {
    id(libs.plugins.alexrdclement.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
    id(libs.plugins.alexrdclement.maven.publish.get().pluginId)
}

kotlin {
    libraryTargets(
        androidNamespace = "com.alexrdclement.uiplayground.trace",
        iosFrameworkBaseName = "Trace",
    )

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.ui)
            }
        }
        androidMain {
            dependencies {
                api(libs.androidx.tracing)
                implementation(libs.androidx.activity.compose)
            }
        }
    }
}
