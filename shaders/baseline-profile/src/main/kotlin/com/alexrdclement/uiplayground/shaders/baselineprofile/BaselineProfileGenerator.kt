package com.alexrdclement.uiplayground.shaders.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alexrdclement.uiplayground.MainCatalogPage
import com.alexrdclement.uiplayground.appPackageName
import com.alexrdclement.uiplayground.shaders.ShadersPage
import com.alexrdclement.uiplayground.shadersPackageName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    // Run with `gradle shaders:generateBaselineProfile`
    @Test
    fun generateShadersProfile() {
        rule.collect(
            packageName = appPackageName,
            filterPredicate = { packageFilterPredicate(shadersPackageName, it) },
        ) {
            pressHome()
            startActivityAndWait()

            MainCatalogPage(device).navigateToShaders()
            val shadersPage = ShadersPage(device)

            shadersPage.selectChromaticAberration()
            shadersPage.adjustChromaticAberration()

            shadersPage.selectNoise()
            shadersPage.adjustNoise()

            shadersPage.selectPixelate()
            shadersPage.adjustPixelate()
        }
    }

    private fun packageFilterPredicate(packageName: String, rule: String): Boolean {
        // Only capture rules in the library's package, excluding test app code
        // Rules are prefixed by tag characters, followed by JVM method signature,
        // e.g. `HSPLcom/mylibrary/LibraryClass;-><init>()V`, where `L`
        // signifies the start of the package/class, and '/' is divider instead of '.'
        return rule.contains("^.*L${packageName.replace(".", "/")}".toRegex())
    }
}
