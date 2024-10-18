plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
}

android {
    namespace = "com.alexrdclement.uiplayground.trace"

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
            baseName = "trace"
            isStatic = true
        }
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.androidx.tracing)
            }
        }
    }
}
