plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
    alias(libs.plugins.uiplayground.compose.multiplatform)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.alexrdclement.uiplayground.theme"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "theme"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.core.ktx)
                implementation(compose.foundation)
                implementation(compose.ui)
            }
        }
        androidMain {
            dependencies {
                implementation(compose.uiTooling)
            }
        }
    }
}
