plugins {
    id(libs.plugins.alexrdclement.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
    id(libs.plugins.alexrdclement.maven.publish.get().pluginId)
}

kotlin {
    libraryTargets(
        androidNamespace = "com.alexrdclement.uiplayground.uievent",
        iosFrameworkBaseName = "UIEvent",
    )

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.tracing)
            }
        }
    }
}
