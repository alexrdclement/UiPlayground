plugins {
    alias(libs.plugins.uiplayground.kotlin.multiplatform)
    alias(libs.plugins.uiplayground.compose.multiplatform)
}

kotlin {
    jvm()
    sourceSets {
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(project(":app"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.alexrdclement.uiplayground.MainKt"
    }
}
