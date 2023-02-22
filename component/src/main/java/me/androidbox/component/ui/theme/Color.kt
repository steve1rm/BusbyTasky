package me.androidbox.component.ui.theme


import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val LightGray = Color(0xFFA1A4B2)
val ExtraLightGray = Color(0xFFF2F3F7)

val ColorScheme.placeholderEntry: Color
    @Composable
    get() {
        return LightGray
    }

val ColorScheme.backgroundInputEntry: Color
    @Composable
    get() {
        return ExtraLightGray
    }