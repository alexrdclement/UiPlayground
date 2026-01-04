plugins {
    id(libs.plugins.alexrdclement.android.benchmark.get().pluginId)
}

android {
    namespace = "com.alexrdclement.uiplayground.benchmark"

    buildTypes {
        create("benchmarkRelease") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
        }
    }

    targetProjectPath = ":androidApp"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

firebaseTestLab {
    managedDevices {
        create(benchmark.deviceName) {
            device = benchmark.deviceType
            apiLevel = benchmark.apiLevel
        }
    }
    val serviceAccountJson = System.getenv("FIREBASE_TEST_LAB_SERVICE_ACCOUNT")
    if (serviceAccountJson != null) {
        serviceAccountCredentials.set(file(serviceAccountJson))
    }
    testOptions {
        results.cloudStorageBucket = "firebase-test-lab-uiplayground"
    }
}

dependencies {
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(libs.androidx.tracing.perfetto)
    implementation(libs.androidx.tracing.perfetto.binary)

    implementation(projects.uiautomatorFixtures)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmarkRelease"
    }
}
