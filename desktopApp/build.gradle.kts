import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id(libs.plugins.alexrdclement.desktop.application.get().pluginId)
    id(libs.plugins.alexrdclement.compose.multiplatform.get().pluginId)
    alias(libs.plugins.compose.hotreload)
}

kotlin {
    desktopAppTarget(
        mainClass = "com.alexrdclement.uiplayground.MainKt",
    )
    sourceSets {
        jvmMain.dependencies {
            implementation(projects.app)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.alexrdclement.uiplayground.MainKt"

        nativeDistributions {
            packageName = "UiPlayground"
            packageVersion = "1.0.0"
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe)
        }
    }
}
