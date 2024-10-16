import com.alexrdclement.uiplayground.convention.AndroidMinSdk
import com.alexrdclement.uiplayground.convention.AndroidTargetSdk
import com.alexrdclement.uiplayground.convention.configureAndroid
import com.alexrdclement.uiplayground.convention.configureKotlin
import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureKotlin()
                configureAndroid()
                defaultConfig.targetSdk = AndroidTargetSdk
                defaultConfig.minSdk = AndroidMinSdk
            }
        }
    }
}
