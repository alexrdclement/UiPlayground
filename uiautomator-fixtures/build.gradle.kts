plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.android)
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
