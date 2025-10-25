plugins {
    id(libs.plugins.alexrdclement.android.test.get().pluginId)
    id(libs.plugins.baselineprofile.get().pluginId)
}

android {
    namespace = "com.alexrdclement.uiplayground.components.baselineprofile"

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
