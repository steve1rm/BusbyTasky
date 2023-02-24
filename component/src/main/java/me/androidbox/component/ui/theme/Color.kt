package me.androidbox.component.ui.theme


import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val LightGray = Color(0xFFA1A4B2)
val DarkGray = Color(0xFF5C5D5A)
val ExtraLightGray = Color(0xFFF2F3F7)
val LightBlue = Color(0xFFB7C6DE)
val Red = Color(0xFFFF7272)
val Black = Color(0xFF16161C)
val White = Color(0xFFFFFFFF)
val LightWhite = Color(0xFFEEF6FF)
val Brown = Color(0xff40492D)
val Green = Color(0xFF279F70)
val LightGreen = Color(0xFFCAEF45)

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

val ColorScheme.focusedInputEntryBorder: Color
    @Composable
    get() {
        return LightBlue
    }

val ColorScheme.errorEmailEntry: Color
    @Composable
    get() {
        return Red
    }

val ColorScheme.buttonColor: Color
    @Composable
    get() {
        return Black
    }

val ColorScheme.LoginTextColor: Color
    @Composable
    get() {
        return LightWhite
    }

val ColorScheme.InputTextColor: Color
    @Composable
    get() {
        return DarkGray
    }

val ColorScheme.DarkOptionButton: Color
    @Composable
    get() {
        return Brown
    }

val ColorScheme.AgendaBodyTextColor: Color
    @Composable
    get() {
        return DarkGray
    }

val ColorScheme.AgendaItemBackgroundGreen: Color
    @Composable
    get() {
        return Green
    }

val ColorScheme.AgendaItemBackgroundLightGreen: Color
    @Composable
    get() {
        return LightGreen
    }
