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

    signingConfigs {
        create("release") {
            storeFile = file("${System.getenv("KEYSTORE_FILE_PATH")}")
            storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
            keyAlias = System.getenv("ANDROID_KEY_ALIAS")
            keyPassword = System.getenv("ANDROID_KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            signingConfig = signingConfigs.getByName("release")

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
    implementation(libs.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.profileinstaller)

    implementation(projects.app)

    baselineProfile(projects.baselineProfile)
}
