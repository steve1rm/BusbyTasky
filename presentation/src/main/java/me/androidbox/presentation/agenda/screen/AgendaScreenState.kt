package me.androidbox.presentation.agenda.screen

import com.maxkeppeker.sheets.core.models.base.UseCaseState
import java.time.ZoneId
import java.time.ZonedDateTime

data class AgendaScreenState(
    val displayMonth: String = ZonedDateTime.now(ZoneId.systemDefault()).month.toString(),
    val usersInitials: String = "",
    val calendarState: UseCaseState = UseCaseState()
)
