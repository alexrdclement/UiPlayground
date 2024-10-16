plugins {
    alias(libs.plugins.uiplayground.android.library)
    alias(libs.plugins.uiplayground.android.library.compose)
    alias(libs.plugins.uiplayground.kotlin.android)
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
    api(libs.compose.foundation)
    api(libs.compose.ui)

    implementation(libs.androidx.tracing)
    implementation(libs.core.ktx)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.material.icons.extended)

    implementation(projects.theme)

    baselineProfile(projects.components.baselineProfile)

    testImplementation(libs.junit)
    testImplementation(libs.test.parameter.injector)
    testImplementation(projects.testing)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.test.manifest)
}
