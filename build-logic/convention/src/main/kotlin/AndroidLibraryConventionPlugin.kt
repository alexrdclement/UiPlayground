import com.alexrdclement.uiplayground.convention.AndroidMinSdk
import com.alexrdclement.uiplayground.convention.AndroidTargetSdk
import com.alexrdclement.uiplayground.convention.configureAndroid
import com.alexrdclement.uiplayground.convention.configureKotlin
import com.alexrdclement.uiplayground.convention.disableUnnecessaryAndroidTests
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureKotlin()
                configureAndroid()
                defaultConfig.targetSdk = AndroidTargetSdk
                defaultConfig.minSdk = AndroidMinSdk
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("testImplementation", kotlin("test"))
            }
        }
    }
}
