plugins {
    id(libs.plugins.alexrdclement.android.baselineprofile.generator.get().pluginId)
}

android {
    namespace = "com.alexrdclement.uiplayground.baselineprofile"

    targetProjectPath = ":androidApp"
}

firebaseTestLab {
    managedDevices {
        create(baselineProfileGenerator.deviceName) {
            device = baselineProfileGenerator.deviceType
            apiLevel = baselineProfileGenerator.apiLevel
        }
    }
    val serviceAccountJson = System.getenv("FIREBASE_TEST_LAB_SERVICE_ACCOUNT")
    if (serviceAccountJson != null) {
        serviceAccountCredentials.set(file(serviceAccountJson))
    }
    testOptions {
        results.cloudStorageBucket = "firebase-test-lab-custom"
    }
}

dependencies {
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)

    implementation(projects.uiautomatorFixtures)
}
