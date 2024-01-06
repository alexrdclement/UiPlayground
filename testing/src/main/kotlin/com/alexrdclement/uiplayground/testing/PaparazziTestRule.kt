package com.alexrdclement.uiplayground.testing

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams

val PaparazziTestRule = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
    renderingMode = SessionParams.RenderingMode.NORMAL,
    showSystemUi = false,
    maxPercentDifference = 1.0,
)
