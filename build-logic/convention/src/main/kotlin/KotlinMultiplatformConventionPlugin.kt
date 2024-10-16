import com.alexrdclement.uiplayground.convention.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
        }

        kotlin {
            applyDefaultHierarchyTemplate()

            jvm()
            if (pluginManager.hasPlugin("com.android.library")) {
                androidTarget {
                    publishLibraryVariants("release")
                }
            }

            iosX64()
            iosArm64()
            iosSimulatorArm64()

            configureKotlin()
        }
    }
}

internal fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure<KotlinMultiplatformExtension>(action)
}

internal val Project.kotlin: KotlinMultiplatformExtension
    get() = extensions.getByType<KotlinMultiplatformExtension>()
