plugins {
    id(libs.plugins.alexrdclement.android.library.asProvider().get().pluginId)
    id(libs.plugins.alexrdclement.android.library.compose.get().pluginId)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.alexrdclement.uiplayground.shaders"
}

dependencies {
    implementation(projects.shaders)
    testImplementation(libs.junit)
    testImplementation(libs.test.parameter.injector)
    testImplementation(projects.testing)
}
