package me.androidbox.presentation.event.screen

import android.net.Uri
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import java.time.ZonedDateTime

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val photoUri: Uri): EventScreenEvent
    /* Visitor Type i.e. All, Going, Not Going */
    data class OnSelectedVisitorType(val visitorType: VisitorType): EventScreenEvent
    data class OnSaveTitleOrDescription(val title: String, val description: String): EventScreenEvent
    data class OnDeleteVisitor(val visitorInfo: VisitorInfo): EventScreenEvent
    data class OnSelectedAgendaAction(val agendaActionType: AgendaActionType): EventScreenEvent
    object OnSaveEventDetails: EventScreenEvent

    data class OnStartTimeDuration(val startTime: ZonedDateTime): EventScreenEvent
    data class OnStartDateDuration(val startDate: ZonedDateTime): EventScreenEvent
    data class OnEndTimeDuration(val endTime: ZonedDateTime): EventScreenEvent
    data class OnEndDateDuration(val endDate: ZonedDateTime): EventScreenEvent
}