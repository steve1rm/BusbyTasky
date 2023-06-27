package me.androidbox.component.agenda

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule


class AgendaDaySelectorKtTest {
    @get:Rule
    private val paparazzi = Paparazzi(
        maxPercentDifference = 0.05,
        deviceConfig = DeviceConfig.PIXEL_6_PRO.copy(softButtons = false)
    )

    fun `test calendar date selection`() {

    }
}