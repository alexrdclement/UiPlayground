import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

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
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "components"
            isStatic = true
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    applyDefaultHierarchyTemplate()

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

dependencies {
    debugImplementation(compose.uiTooling)
}
