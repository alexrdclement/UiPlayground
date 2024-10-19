plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
    alias(libs.plugins.uiplayground.compose.multiplatform)
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
            implementation(libs.core.ktx)
            implementation(libs.androidx.navigation.compose)

            implementation(compose.foundation)
            implementation(compose.ui)

            implementation(projects.components)
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
