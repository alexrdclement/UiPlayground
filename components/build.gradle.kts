plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
    alias(libs.plugins.uiplayground.compose.multiplatform)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.alexrdclement.uiplayground.components"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

baselineProfile {
    filter {
        include("com.alexrdclement.uiplayground.components.**")
    }
}

dependencies {
    baselineProfile(projects.components.baselineProfile)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)

                implementation(libs.core.ktx)
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor3)

                implementation(projects.theme)
                implementation(projects.trace)
            }
        }
        androidMain {
            dependencies {
                implementation(compose.uiTooling)
                implementation(libs.ktor.client.android)
                implementation(libs.compose.ui.test.manifest)
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
                implementation(libs.ktor.client.darwin)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.ktor.client.java)
            }
        }
    }
}
