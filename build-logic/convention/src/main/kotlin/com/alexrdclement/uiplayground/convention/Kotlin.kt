package com.alexrdclement.uiplayground.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

fun Project.configureKotlin(enableAllWarningsAsErrors: Boolean = false) {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        compilerOptions {
            // Blocked by https://youtrack.jetbrains.com/issue/KT-69701/
            if (enableAllWarningsAsErrors) {
                allWarningsAsErrors = true
            }

            if (this is KotlinJvmCompilerOptions) {
                jvmTarget = JvmTarget.JVM_17
                // Enable detailed names in test-parameter-injector tests
                javaParameters = true
            }
        }
    }
}
