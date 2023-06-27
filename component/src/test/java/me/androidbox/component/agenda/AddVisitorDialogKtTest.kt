package me.androidbox.component.agenda

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class AddVisitorDialogKtTest {
    @get:Rule
    val paparazzi = Paparazzi(
        maxPercentDifference = 0.05,
        deviceConfig = DeviceConfig.PIXEL_6_PRO.copy(softButtons = false)
    )

    @Test
    fun addVisitorDialogEmailNotExist() {
        paparazzi.snapshot {
            PreviewAddVisitorDialogEmailNotExist()
        }
    }
}