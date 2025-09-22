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
    namespace = "com.alexrdclement.uiplayground.shaders"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

baselineProfile {
    filter {
        include("com.alexrdclement.uiplayground.shaders.**")
    }
}

dependencies {
    baselineProfile(projects.shaders.baselineProfile)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shaders"
            isStatic = true
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.uiToolingPreview)

                api(projects.core)
            }
        }
        androidMain {
            dependencies {
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

dependencies {
    debugImplementation(compose.uiTooling)
}
