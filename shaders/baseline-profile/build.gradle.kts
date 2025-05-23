plugins {
    // See https://github.com/gradle/gradle/issues/17968
    id(libs.plugins.uiplayground.android.test.get().pluginId)
    id(libs.plugins.baselineprofile.get().pluginId)
}

android {
    namespace = "com.alexrdclement.uiplayground.shaders.baselineprofile"

    targetProjectPath = ":androidApp"
}

baselineProfile {
    useConnectedDevices = true
}

dependencies {
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)

    implementation(projects.uiautomatorFixtures)
}
