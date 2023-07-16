package me.androidbox.presentation.event.screen

import java.time.ZonedDateTime
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.AlarmReminderItem
import me.androidbox.component.agenda.VisitorFilterType
import me.androidbox.domain.agenda.model.Attendee

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val photoUri: String) : EventScreenEvent
    data class OnPhotoDeletion(val photo: String) : EventScreenEvent
    data class OnSaveTitleOrDescription(val title: String, val description: String) : EventScreenEvent
    data class OnDeleteVisitor(val userId: String) : EventScreenEvent
    data class OnAttendeeStatusUpdate(val isGoing: Boolean) : EventScreenEvent
    data class OnVisitorEmailChanged(val visitorEmail: String) : EventScreenEvent
    data class OnShowVisitorDialog(val shouldShowVisitorDialog: Boolean) : EventScreenEvent
    data class CheckVisitorExists(val visitorEmail: String) : EventScreenEvent

    /* Visitor Type i.e. All, Going, Not Going */
    data class CheckVisitorAlreadyAdded(val isAlreadyAdded: Boolean) : EventScreenEvent
    data class OnVisitorFilterTypeChanged(val visitorFilterType: VisitorFilterType) : EventScreenEvent
    object LoadVisitors : EventScreenEvent
    data class OnVerifyingVisitorEmail(val isVerifyingVisitorEmail: Boolean) : EventScreenEvent
    data class OnSelectedAgendaAction(val agendaActionType: AgendaActionType) : EventScreenEvent
    object OnSaveEventDetails : EventScreenEvent
    data class OnStartTimeDuration(val startTime: ZonedDateTime) : EventScreenEvent
    data class OnStartDateDuration(val startDate: ZonedDateTime) : EventScreenEvent
    data class OnEndTimeDuration(val endTime: ZonedDateTime) : EventScreenEvent
    data class OnEndDateDuration(val endDate: ZonedDateTime) : EventScreenEvent
    data class OnStartDateTimeChanged(val isStartDateTime: Boolean) : EventScreenEvent
    data class OnShowAlarmReminderDropdown(val shouldOpen: Boolean) : EventScreenEvent
    data class OnAlarmReminderChanged(val reminderItem: AlarmReminderItem) : EventScreenEvent
    data class OnAttendeeAdded(val attendee: Attendee) : EventScreenEvent
    data class OnAttendeeDeleted(val attendee: Attendee) : EventScreenEvent
    data class OnShowDeleteEventAlertDialog(val shouldShowDeleteAlertDialog: Boolean) : EventScreenEvent
    data class OnDeleteEvent(val eventId: String) : EventScreenEvent
    data class OnEditModeChangeStatus(val isEditModel: Boolean) : EventScreenEvent
}