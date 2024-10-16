plugins {
    alias(libs.plugins.uiplayground.android.test)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.alexrdclement.uiplayground.baselineprofile"

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

androidComponents {
    onVariants { v ->
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { v.artifacts.getBuiltArtifactsLoader().load(it)?.applicationId }
        )
    }
}
