package me.androidbox.component.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val LightGray = Color(0xFFA1A4B2)
val Light = Color(0xFFEEF6FF)
val Light2 = Color(0xFFF2F3F7)
val DarkGray = Color(0xFF5C5D5A)
val Gray = Color(0xFFA9B4BE)
val ExtraLightGray = Color(0xFFF2F3F7)
val LightBlue = Color(0xFFB7C6DE)
val Red = Color(0xFFFF7272)
val Black = Color(0xFF16161C)
val White = Color(0xFFFFFFFF)
val LightWhite = Color(0xFFEEF6FF)
val ExtraLightWhite = Color(0xFFf5f5f5)
val Brown = Color(0xff40492D)
val Green = Color(0xFF279F70)
val LightGreen = Color(0xFFCAEF45)
val Background = Color(0xFFF5F5F5)
val Orange = Color(0xFFFDEFA8)

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

val ColorScheme.loginTextColor: Color
    @Composable
    get() {
        return LightWhite
    }

val ColorScheme.inputTextColor: Color
    @Composable
    get() {
        return DarkGray
    }

val ColorScheme.darkOptionButton: Color
    @Composable
    get() {
        return Brown
    }

val ColorScheme.agendaCardItemBodyTextColor: Color
    @Composable
    get() {
        return DarkGray
    }

val ColorScheme.TaskCardBackgroundColor: Color
    @Composable
    get() {
        return Green
    }

val ColorScheme.EventCardBackgroundColor: Color
    @Composable
    get() {
        return LightGreen
    }

val ColorScheme.ReminderCardBackgroundColor: Color
    @Composable
    get() {
        return Light2
    }

val ColorScheme.agendaItemBackgroundLightGray: Color
    @Composable
    get() {
        return LightGray
    }

val ColorScheme.divider: Color
    @Composable
    get() {
        return LightWhite
    }

val ColorScheme.headerDividerColor: Color
    @Composable
    get() {
        return Light
    }

val ColorScheme.dividerBlack: Color
    @Composable
    get() {
        return Black
    }

val ColorScheme.agendaTitleHeaderColor: Color
    @Composable
    get() {
        return DarkGray
    }

val ColorScheme.agendaSubTitleHeaderColor: Color
    @Composable
    get() {
        return Black
    }

val ColorScheme.agendaBodyTextColor: Color
    @Composable
    get() {
        return Black
    }

val ColorScheme.backgroundWhiteColor: Color
    @Composable
    get() {
        return Background
    }

val ColorScheme.backgroundBackColor: Color
    @Composable
    get() {
        return Black
    }

val ColorScheme.topbarFontColor: Color
    @Composable
    get() {
        return LightBlue
    }

val ColorScheme.topbarButtonBackgroundColor: Color
    @Composable
    get() {
        return Light
    }

val ColorScheme.agendaBackgroundColor: Color
    @Composable
    get() {
        return White
    }

val ColorScheme.visitorBackgroundColor: Color
    @Composable
    get() {
        return ExtraLightGray
    }

val ColorScheme.visitorInitialsFontColor: Color
    @Composable
    get() {
        return White
    }

val ColorScheme.fontWhiteColor: Color
    @Composable
    get() {
        return White
    }

val ColorScheme.visitorSelectedWhiteBackgroundColor: Color
    @Composable
    get() {
        return Light2
    }

val ColorScheme.visitorTextFontColor: Color
    @Composable
    get() {
        return DarkGray
    }

val ColorScheme.creatorTextFontColor: Color
    @Composable
    get() {
        return LightBlue
    }

val ColorScheme.topbarText: Color
    @Composable
    get() {
        return Green
    }

val ColorScheme.editScreenBackground: Color
    @Composable
    get() {
        return ExtraLightWhite
    }

val ColorScheme.dropDownMenuColor: Color
    @Composable
    get() {
        return Black
    }

val ColorScheme.dropDownMenuBackgroundColor: Color
    @Composable
    get() {
        return ExtraLightWhite
    }

val ColorScheme.photoTextColor: Color
    @Composable
    get() {
        return Gray
    }

val ColorScheme.photoBackgroundColor: Color
    @Composable
    get() {
        return ExtraLightGray
    }

val ColorScheme.photoPickerBorderColor: Color
    @Composable
    get() {
        return LightBlue
    }
