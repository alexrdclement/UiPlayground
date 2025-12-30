plugins {
    id(libs.plugins.alexrdclement.android.library.asProvider().get().pluginId)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.alexrdclement.uiplayground.testing"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(libs.junit)
    api(libs.paparazzi)
}
