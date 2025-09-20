package com.alexrdclement.uiplayground.components.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alexrdclement.uiplayground.MainCatalogPage
import com.alexrdclement.uiplayground.appPackageName
import com.alexrdclement.uiplayground.components.ComponentsPage
import com.alexrdclement.uiplayground.components.media.MediaComponentsPage
import com.alexrdclement.uiplayground.components.media.MediaControlSheetPage
import com.alexrdclement.uiplayground.componentsPackageName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    // Run with `gradle components:generateBaselineProfile`
    @Test
    fun generateComponentsProfile() {
        rule.collect(
            packageName = appPackageName,
            filterPredicate = { packageFilterPredicate(componentsPackageName, it) },
        ) {
            pressHome()
            startActivityAndWait()

            MainCatalogPage(device).navigateToComponents()
            ComponentsPage(device).navigateToMediaComponents()
            MediaComponentsPage(device).navigateToMediaControlSheet()

            MediaControlSheetPage(device).mediaControlBar.click()
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
