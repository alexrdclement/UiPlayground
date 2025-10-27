plugins {
    id(libs.plugins.alexrdclement.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
    id(libs.plugins.alexrdclement.maven.publish.get().pluginId)

    // TODO
//    alias(libs.plugins.baselineprofile)
}

kotlin {
    libraryTargets(
        androidNamespace = "com.alexrdclement.uiplayground.shaders",
        iosFrameworkBaseName = "Shaders",
    )

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.uiToolingPreview)

                api(projects.trace)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.compose.ui.test.manifest)
            }
        }

        val skikoMain by creating {
            dependsOn(commonMain.get())
        }

        iosMain {
            dependsOn(skikoMain)
        }
        jvmMain {
            dependsOn(skikoMain)
        }
        wasmJsMain {
            dependsOn(skikoMain)
        }
    }
}

// TODO
//baselineProfile {
//    filter {
//        include("com.alexrdclement.uiplayground.shaders.**")
//    }
//}
//
//dependencies {
//    baselineProfile(projects.shaders.baselineProfile)
//}
//
//dependencies {
//    debugImplementation(compose.uiTooling)
//}
