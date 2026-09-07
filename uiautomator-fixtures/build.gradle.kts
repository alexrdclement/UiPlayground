plugins {
    id(libs.plugins.embarrasdf.android.library.asProvider().get().pluginId)
}

android {
    namespace = "com.alexrdclement.uiplayground"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(libs.androidx.uiautomator)
}
