import com.alexrdclement.uiplayground.convention.AndroidMinSdk
import com.alexrdclement.uiplayground.convention.AndroidTargetSdk
import com.alexrdclement.uiplayground.convention.configureAndroid
import com.alexrdclement.uiplayground.convention.configureKotlin
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlin()
                configureAndroid()
                defaultConfig.targetSdk = AndroidTargetSdk
                defaultConfig.minSdk = AndroidMinSdk
            }
        }
    }
}
