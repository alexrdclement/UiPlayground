package com.alexrdclement.uiplayground.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findLibrary("compose.ui.tooling.preview").get())
            add("androidTestImplementation", platform(bom))
            add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())
        }
    }
}
