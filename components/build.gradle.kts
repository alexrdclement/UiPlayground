plugins {
    id(libs.plugins.alexrdclement.kotlin.multiplatform.library.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
//    id(libs.plugins.alexrdclement.android.baselineprofile.consumer.get().pluginId)
    id(libs.plugins.alexrdclement.maven.publish.get().pluginId)

    // TODO
//    alias(libs.plugins.paparazzi)
    alias(libs.plugins.baselineprofile) apply true
}

kotlin {
    libraryTargets(
        androidNamespace = "com.alexrdclement.uiplayground.components",
        iosFrameworkBaseName = "Components",
    )

    sourceSets {
        commonMain {
            dependencies {
                api(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.uiToolingPreview)

                api(libs.coil.compose)
                api(libs.coil.network.ktor3)

                api(libs.kotlinx.collections.immutable)

                api(projects.trace)
                api(projects.theme)
            }
        }
        androidMain {
            dependencies {
                api(libs.ktor.client.android)
                api(libs.ktor.client.okhttp)
                implementation(compose.preview)
                implementation(libs.compose.ui.test.manifest)
                implementation(libs.androidx.emoji2)
                implementation(libs.androidx.activity.compose)
                implementation(compose.uiTooling)
            }
        }
        androidUnitTest {
            dependencies {
                implementation(libs.junit)
                implementation(libs.test.parameter.injector)
                implementation(projects.testing)
            }
        }
        androidInstrumentedTest {
            dependencies {
                implementation(libs.androidx.test.ext.junit)
                implementation(libs.compose.ui.test.junit4)
            }
        }
        appleMain {
            dependencies {
                api(libs.ktor.client.darwin)
            }
        }
        jvmMain {
            dependencies {
                api(libs.ktor.client.java)
            }
        }
    }
}

//dependencies {
//    baselineProfile(projects.components.baselineProfile)
//}
//
//baselineProfile {
//    filter {
//        include("com.alexrdclement.uiplayground.components.**")
//    }
//}
