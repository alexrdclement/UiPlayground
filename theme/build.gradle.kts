plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.kotlin.android)
    alias(libs.plugins.uiplayground.android.library.compose)
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

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.compose.foundation)
}
