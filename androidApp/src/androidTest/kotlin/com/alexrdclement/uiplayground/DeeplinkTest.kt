package com.alexrdclement.uiplayground

import android.content.Intent
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DeeplinkTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun noIntentUri() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.startActivity(
            context.packageManager.getLaunchIntentForPackage(context.packageName)!!
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        MainCatalogPage(device).assertIsDisplayed()
    }

    @Test
    fun emptyDeeplinkPath() {
        startDeeplink("")
        MainCatalogPage(device).assertIsDisplayed()
    }

    @Test
    fun mainGraph() {
        startDeeplink("main")
        MainCatalogPage(device).assertIsDisplayed()
    }

    @Test
    fun mainCatalog() {
        startDeeplink("main/catalog")
        MainCatalogPage(device).assertIsDisplayed()
    }

    @Test
    fun experimentsCatalog() {
        startDeeplink("experiments/catalog")
        ExperimentCatalogPage(device).assertIsDisplayed()
    }

    @Test
    fun configuration() {
        startDeeplink("configuration")
        ConfigurationPage(device).assertIsDisplayed()
    }

    @Test
    fun gradientsExperiment() {
        startDeeplink("experiments/gradients")
        ExperimentPage(device, title = "Gradients").assertIsDisplayed()
    }

    @Test
    fun keyboardExperiment() {
        startDeeplink("experiments/keyboard")
        ExperimentPage(device, title = "Keyboard").assertIsDisplayed()
    }

    private fun startDeeplink(path: String) {
        InstrumentationRegistry.getInstrumentation().targetContext.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("uiplayground://app/$path"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
