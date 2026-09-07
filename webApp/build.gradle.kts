plugins {
    id(libs.plugins.embarrasdf.web.application.get().pluginId)
    id(libs.plugins.embarrasdf.compose.multiplatform.get().pluginId)
}

kotlin {
    webAppTarget()

    sourceSets {
        wasmJsMain {
            dependencies {
                implementation(projects.app)
                implementation(libs.navigation3.browser)
                implementation(libs.palette.navigation)
            }
        }
    }
}
