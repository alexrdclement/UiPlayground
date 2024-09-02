plugins {
    alias(libs.plugins.uiplayground.android.library)
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
    implementation(libs.core.ktx)
    implementation(libs.androidx.uiautomator)
}
