import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.alexrdclement.uiplayground.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("composeMultiplatform") {
            id = "uiplayground.compose.multiplatform"
            implementationClass = "ComposeMultiplatformConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "uiplayground.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "uiplayground.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "uiplayground.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "uiplayground.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidTest") {
            id = "uiplayground.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("kotlinAndroid") {
            id = "uiplayground.kotlin.android"
            implementationClass = "KotlinAndroidConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "uiplayground.kotlin.multiplatform"
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }
    }
}
