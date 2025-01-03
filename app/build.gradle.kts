plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
    alias(libs.plugins.uiplayground.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
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

    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.ui)

            implementation(libs.core.ktx)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation.compose)

            implementation(projects.components)
            implementation(projects.core)
            implementation(projects.shaders)
            implementation(projects.theme)
        }
        androidMain {
            dependencies {
                implementation(compose.uiTooling)
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
