plugins {
    id(libs.plugins.alexrdclement.android.library.asProvider().get().pluginId)
    id(libs.plugins.alexrdclement.android.library.compose.get().pluginId)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.alexrdclement.uiplayground.components"
}

dependencies {
    implementation(projects.components)
    testImplementation(libs.junit)
    testImplementation(libs.test.parameter.injector)
    testImplementation(projects.components)
    testImplementation(projects.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
}
