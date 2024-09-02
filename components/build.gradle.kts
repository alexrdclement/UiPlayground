plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.android.library.compose)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.alexrdclement.uiplayground.components"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

baselineProfile {
    filter {
        include("com.alexrdclement.uiplayground.components.**")
    }
}

dependencies {
    implementation(libs.androidx.tracing)
    implementation(libs.core.ktx)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    baselineProfile(projects.components.baselineProfile)

    testImplementation(libs.junit)
    testImplementation(libs.test.parameter.injector)
    testImplementation(projects.testing)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.test.manifest)
}
