plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.alexrdclement.uiplayground.components"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        javaParameters = true // Enable detailed names in test-parameter-injector tests
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(libs.androidx.tracing)
    implementation(libs.core.ktx)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    testImplementation(libs.junit)
    testImplementation(libs.test.parameter.injector)
    testImplementation(projects.testing)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
