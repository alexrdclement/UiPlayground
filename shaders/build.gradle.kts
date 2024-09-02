plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.android.library.compose)
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
    implementation(libs.androidx.tracing)
    implementation(libs.core.ktx)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.material3)

    baselineProfile(projects.shaders.baselineProfile)

    testImplementation(libs.junit)
    testImplementation(libs.test.parameter.injector)
    testImplementation(projects.testing)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    debugImplementation(libs.compose.ui.test.manifest)
}
