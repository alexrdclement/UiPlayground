plugins {
    alias(libs.plugins.uiplayground.android.application)
    alias(libs.plugins.uiplayground.android.application.compose)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.alexrdclement.uiplayground"

    defaultConfig {
        applicationId = "com.alexrdclement.uiplayground"
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            // Not planning to release the app. Doing this for simplicity.
            signingConfig = signingConfigs.getByName("debug")

            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime.tracing)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    implementation(projects.components)
    implementation(projects.shaders)

    baselineProfile(projects.baselineProfile)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.test.manifest)
}
