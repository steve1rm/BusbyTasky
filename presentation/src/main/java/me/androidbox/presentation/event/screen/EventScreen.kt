package me.androidbox.presentation.event.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.androidbox.component.R
import me.androidbox.component.agenda.AddVisitorDialog
import me.androidbox.component.agenda.AgendaAction
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.AgendaDetailTopBar
import me.androidbox.component.agenda.AgendaDuration
import me.androidbox.component.agenda.AgendaHeader
import me.androidbox.component.agenda.AgendaHeaderItem
import me.androidbox.component.agenda.AlarmReminder
import me.androidbox.component.agenda.AlarmReminderItem
import me.androidbox.component.agenda.DeleteEventAlertDialog
import me.androidbox.component.agenda.EditModeType
import me.androidbox.component.agenda.VisitorFilter
import me.androidbox.component.agenda.VisitorFilterType
import me.androidbox.component.general.AgendaBottomSheet
import me.androidbox.component.general.AlarmReminderOptions
import me.androidbox.component.general.PhotoPicker
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.backgroundWhiteColor
import me.androidbox.domain.DateTimeFormatterProvider.DATE_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.LONG_DATE_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.TIME_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.formatDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    eventScreenState: EventScreenState,
    eventScreenEvent: (EventScreenEvent) -> Unit,
    onEditTitleClicked: (title: String) -> Unit,
    onEditDescriptionClicked: (description: String) -> Unit,
    onCloseClicked: () -> Unit,
    onPhotoClicked: (photo: String) -> Unit,
    modifier: Modifier = Modifier) {

    val calendarStateTimeDate = rememberUseCaseState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaDetailTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                    .padding(horizontal = 16.dp),
                editModeType = if(eventScreenState.isEditMode) { EditModeType.SaveMode() } else { EditModeType.EditMode() },
                displayDate = eventScreenState.startDate.formatDateTime(LONG_DATE_PATTERN),
                onCloseClicked = onCloseClicked,
                onEditClicked = {
                    eventScreenEvent(EventScreenEvent.OnEditModeChangeStatus(isEditModel = true))
                },
                onSaveClicked = {
                    eventScreenEvent(EventScreenEvent.OnSaveEventDetails)
                })

        },
        content = {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .fillMaxWidth()
                .background(Color.Black)) {

                Column (modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = modifier.height(30.dp))
                    AgendaHeader(
                        agendaHeaderItem = AgendaHeaderItem.EVENT,
                        subTitle = eventScreenState.eventTitle,
                        description = eventScreenState.eventDescription,
                        isEditMode = eventScreenState.isEditMode,
                        onEditTitleClicked = { newTitle ->
                            onEditTitleClicked(newTitle)
                        },
                        onEditDescriptionClicked = { newDescription ->
                            onEditDescriptionClicked(newDescription)
                        }
                    )

                    PhotoPicker(
                        listOfPhotoUri = eventScreenState.listOfPhotoUri,
                        onPhotoUriSelected = { uri ->
                            eventScreenEvent(EventScreenEvent.OnPhotoUriAdded(uri.toString()))
                        },
                        onOpenPhoto = { uri ->
                            onPhotoClicked(uri)
                        }
                    )

                    Spacer(modifier = modifier.height(26.dp))
                    AgendaDuration(
                        isEditMode = eventScreenState.isEditMode,
                        startTime = eventScreenState.startTime.formatDateTime(TIME_PATTERN),
                        endTime = eventScreenState.endTime.formatDateTime(TIME_PATTERN),
                        startDate = eventScreenState.startDate.formatDateTime(DATE_PATTERN),
                        endDate = eventScreenState.endDate.formatDateTime(DATE_PATTERN),
                        modifier = Modifier.fillMaxWidth(),
                        onStartDurationClicked = {
                            eventScreenEvent(EventScreenEvent.OnStartDateTimeChanged(isStartDateTime = true))
                            calendarStateTimeDate.show()
                        },
                        onEndDurationClicked = {
                            eventScreenEvent(EventScreenEvent.OnStartDateTimeChanged(isStartDateTime = false))
                            calendarStateTimeDate.show()
                        }
                    )

                    Spacer(modifier = modifier.height(26.dp))

                    AlarmReminder(
                        reminderText = stringResource(id = eventScreenState.alarmReminderItem.stringResId),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.backgroundWhiteColor),
                        isEditMode = eventScreenState.isEditMode,
                        onReminderClicked = {
                            eventScreenEvent(EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = true))
                            bottomSheetScope.launch {
                                bottomSheetState.show()
                            }
                        }
                    )
                    Spacer(modifier = modifier.height(26.dp))

                    VisitorFilter(
                        modifier = Modifier.fillMaxWidth(),
                        selectedVisitorType = VisitorFilterType.ALL,
                        onSelectedTypeClicked = {},
                        onAddVisitorClicked = {
                            eventScreenEvent(EventScreenEvent.OnShowVisitorDialog(shouldShowVisitorDialog = true))
                        }
                    )

                    Spacer(modifier = modifier.height(26.dp))

                    AgendaAction(
                        agendaActionType = if(eventScreenState.isUserEventCreator) { AgendaActionType.DELETE_EVENT } else { AgendaActionType.JOIN_EVENT },
                        onActionClicked = { agendaActionType ->
                            when(agendaActionType) {
                                AgendaActionType.DELETE_EVENT -> {
                                    eventScreenEvent(EventScreenEvent.OnShowDeleteEventAlertDialog(shouldShowDeleteAlertDialog = true))
                                }
                                AgendaActionType.JOIN_EVENT -> {

                                }
                                AgendaActionType.LEAVE_EVENT -> {

                                }
                                else -> Unit
                            }

                        }
                    )
                }
            }
        }
    )

    AgendaBottomSheet(
        shouldOpenBottomSheet = eventScreenState.shouldOpenDropdown,
        bottomSheetState = bottomSheetState,
        coroutineScope = bottomSheetScope,
        onCloseDropdown = {
            eventScreenEvent(EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))
        },
        content = {
            AlarmReminderOptions(
                title = stringResource(id = R.string.alarm_reminder),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                listOfMenuItemId = AlarmReminderItem.values()
                    .map { alarmReminderItem ->
                        alarmReminderItem.stringResId
                    },
                onSelectedOption = { item ->
                    eventScreenEvent(
                        EventScreenEvent.OnAlarmReminderChanged(
                            AlarmReminderItem.values()[item]
                        )
                    )
                    bottomSheetScope.launch {
                        bottomSheetState.hide()
                        eventScreenEvent(EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))
                    }
                })
        })

    if(eventScreenState.shouldShowVisitorDialog) {
        AddVisitorDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.backgroundWhiteColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            email = eventScreenState.visitorEmail,
            onEmailChanged = { visitorEmail ->
                eventScreenEvent(EventScreenEvent.OnVisitorEmailChanged(visitorEmail))
            },
            onDialogClose = {
                eventScreenEvent(EventScreenEvent.OnShowVisitorDialog(shouldShowVisitorDialog = false))
            },
            isValidInput = false,
            onAddButtonClicked = { visitorEmail ->
                eventScreenEvent(EventScreenEvent.CheckVisitorExists(visitorEmail))
            },
            isEmailVerified = eventScreenState.isEmailVerified
        )
    }

    DateTimeDialog(
        state = calendarStateTimeDate,
        selection = DateTimeSelection.DateTime(
            selectedDate = ZonedDateTime.now().toLocalDate(),
            selectedTime = ZonedDateTime.now().toLocalTime(),
        ) { localDateTime ->
            val zoneId = ZoneId.systemDefault()
            val zonedDateTime = ZonedDateTime.of(localDateTime, zoneId)

            if(eventScreenState.isStartDateTime) {
                eventScreenEvent(EventScreenEvent.OnStartTimeDuration(zonedDateTime))
                eventScreenEvent(EventScreenEvent.OnStartDateDuration(zonedDateTime))
            }
            else {
                eventScreenEvent(EventScreenEvent.OnEndTimeDuration(zonedDateTime))
                eventScreenEvent(EventScreenEvent.OnEndDateDuration(zonedDateTime))
            }
        }
    )

    if(eventScreenState.shouldShowDeleteAlertDialog) {
        DeleteEventAlertDialog(
            title = stringResource(id = R.string.delete_event),
            text = stringResource(id = R.string.confirm_delete_event),
            onConfirmationClicked = {
                eventScreenEvent(EventScreenEvent.OnDeleteEvent(eventScreenState.eventId))
                onCloseClicked()
            },
            onDismissClicked = {
                eventScreenEvent(EventScreenEvent.OnShowDeleteEventAlertDialog(shouldShowDeleteAlertDialog = false))
            })
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEventScreenEditMode() {
    BusbyTaskyTheme {
        EventScreen(
            eventScreenState = EventScreenState(
                isEditMode = false
            ),
            eventScreenEvent = {},
            modifier = Modifier.fillMaxWidth(),
            onEditDescriptionClicked = {},
            onEditTitleClicked = {},
            onCloseClicked = {},
            onPhotoClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEventScreenSaveMode() {
    BusbyTaskyTheme {
        EventScreen(
            eventScreenState = EventScreenState(
                isEditMode = true
            ),
            eventScreenEvent = {},
            modifier = Modifier.fillMaxWidth(),
            onEditDescriptionClicked = {},
            onEditTitleClicked = {},
            onCloseClicked = {},
            onPhotoClicked = {}
        )
    }
}