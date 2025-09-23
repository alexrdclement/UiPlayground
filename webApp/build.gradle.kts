import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
    alias(libs.plugins.uiplayground.compose.multiplatform)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        wasmJsMain {
            dependencies {
                implementation(project(":app"))
                implementation(compose.ui)
            }
        }
    }
}
