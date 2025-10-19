import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
    alias(libs.plugins.uiplayground.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "app"
            isStatic = true
        }
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation.compose)
            implementation(libs.log.core)

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

android {
    namespace = "com.alexrdclement.uiplayground.app"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}
