package me.androidbox.presentation.event.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import me.androidbox.component.R
import me.androidbox.component.agenda.*
import me.androidbox.component.event.VisitorInfo
import me.androidbox.component.event.VisitorItem
import me.androidbox.component.general.AgendaDropDownMenu
import me.androidbox.component.general.PhotoPicker
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.backgroundWhiteColor
import me.androidbox.component.ui.theme.dropDownMenuBackgroundColor
import me.androidbox.component.ui.theme.visitorTextFontColor
import me.androidbox.domain.DateTimeFormatterProvider.DATE_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.TIME_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.formatDateTime
import me.androidbox.domain.agenda.usecase.toInitials
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    eventScreenState: EventScreenState,
    eventScreenEvent: (EventScreenEvent) -> Unit,
    onEditTitleClicked: (title: String) -> Unit,
    onEditDescriptionClicked: (description: String) -> Unit,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    val calendarStateTimeDate = rememberUseCaseState()

    LaunchedEffect(key1 = eventScreenState.attendees) {
        eventScreenEvent(EventScreenEvent.LoadVisitors)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaDetailTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                    .padding(horizontal = 16.dp),
                editModeType = EditModeType.SaveMode(),
                displayDate = "31 March 2023", /* TODO Get the date from the agenda screen that was selected by the user */
                onCloseClicked = onCloseClicked,
                onEditClicked = {

                },
                onSaveClicked = {
                    eventScreenEvent(EventScreenEvent.OnSaveEventDetails)
                })

        },
        content = {
/*
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .fillMaxWidth()
                .background(Color.Black)) {
*/

                LazyColumn (modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(it),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    item {
                        Spacer(modifier = modifier.height(30.dp))
                        AgendaHeader(
                            agendaHeaderItem = AgendaHeaderItem.EVENT,
                            subTitle = eventScreenState.eventTitle,
                            description = eventScreenState.eventDescription,
                            isEditMode = true,
                            onEditTitleClicked = { newTitle ->
                                onEditTitleClicked(newTitle)
                            },
                            onEditDescriptionClicked = { newDescription ->
                                onEditDescriptionClicked(newDescription)
                            }
                        )
                    }

                    item {
                       PhotoPicker(
                            listOfPhotoUri = eventScreenState.listOfPhotoUri,
                            onPhotoUriSelected = { uri ->
                                eventScreenEvent(EventScreenEvent.OnPhotoUriAdded(uri.toString()))
                            }
                        )
                   }

                    item {
                      Spacer(modifier = modifier.height(26.dp))
                        AgendaDuration(
                            isEditMode = true,
                            startTime = eventScreenState.startTime.formatDateTime(TIME_PATTERN),
                            endTime = eventScreenState.endTime.formatDateTime(TIME_PATTERN),
                            startDate = eventScreenState.startDate.formatDateTime(DATE_PATTERN),
                            endDate = eventScreenState.endDate.formatDateTime(DATE_PATTERN),
                            modifier = Modifier.fillMaxWidth(),
                            onStartDurationClicked = {
                                eventScreenEvent(
                                    EventScreenEvent.OnStartDateTimeChanged(
                                        isStartDateTime = true
                                    )
                                )
                                calendarStateTimeDate.show()
                            },
                            onEndDurationClicked = {
                                eventScreenEvent(
                                    EventScreenEvent.OnStartDateTimeChanged(
                                        isStartDateTime = false
                                    )
                                )
                                calendarStateTimeDate.show()
                            }
                        )
                    }

                    item {
                        Spacer(modifier = modifier.height(26.dp))
                        AgendaDropDownMenu(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor),
                            shouldOpenDropdown = eventScreenState.shouldOpenDropdown,
                            onCloseDropdown = {
                                eventScreenEvent(
                                    EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false)
                                )
                            },
                            listOfMenuItemId = AlarmReminderItem.values().map { alarmReminderItem ->
                                alarmReminderItem.stringResId
                            },
                            onSelectedOption = { item ->
                                eventScreenEvent(
                                    EventScreenEvent.OnAlarmReminderChanged(
                                        AlarmReminderItem.values()[item]
                                    )
                                )
                                eventScreenEvent(
                                    EventScreenEvent.OnShowAlarmReminderDropdown(
                                        shouldOpen = false
                                    )
                                )
                            }
                        )
                    }

                    item {
                        AlarmReminder(
                            reminderText = stringResource(id = eventScreenState.alarmReminderItem.stringResId),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.backgroundWhiteColor),
                            onReminderClicked = {
                                eventScreenEvent(
                                    EventScreenEvent.OnShowAlarmReminderDropdown(
                                        shouldOpen = true
                                    )
                                )
                            }
                        )
                    }

                    item {
                        Spacer(modifier = modifier.height(26.dp))
                        VisitorFilter(
                            modifier = Modifier.fillMaxWidth(),
                            selectedVisitorType = eventScreenState.selectedVisitorFilterType,
                            onSelectedTypeClicked = { visitorFilterType ->
                                eventScreenEvent(
                                    EventScreenEvent.OnVisitorFilterTypeChanged(
                                        visitorFilterType
                                    )
                                )
                            },
                            onAddVisitorClicked = {
                                eventScreenEvent(
                                    EventScreenEvent.OnShowVisitorDialog(
                                        shouldShowVisitorDialog = true
                                    )
                                )
                            }
                        )
                    }

                    if(eventScreenState.selectedVisitorFilterType == VisitorFilterType.ALL || eventScreenState.selectedVisitorFilterType == VisitorFilterType.GOING) {
                        /** Going section */
                        item {
                            Text(
                                text = stringResource(id = R.string.going),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.visitorTextFontColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                        }
                        if(eventScreenState.filteredVisitorsGoing.isNotEmpty()) {
                            items(eventScreenState.filteredVisitorsGoing) { attendee ->
                                VisitorItem(
                                    initials = attendee.fullName.toInitials(),
                                    userId = attendee.userId,
                                    fullName = attendee.fullName,
                                    onDeleteClicked = { userId ->
                                        eventScreenEvent(EventScreenEvent.OnDeleteVisitor(userId))
                                    },
                                    isCreator = eventScreenState.host == attendee.userId
                                )
                            }
                        }
                    }

                    /** Not going section */
                    if(eventScreenState.selectedVisitorFilterType == VisitorFilterType.ALL || eventScreenState.selectedVisitorFilterType == VisitorFilterType.NOT_GOING) {
                        item {
                            Text(
                                text = stringResource(id = R.string.not_going),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.visitorTextFontColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        if(eventScreenState.filteredVisitorsNotGoing.isNotEmpty()) {
                            items(eventScreenState.filteredVisitorsNotGoing) { attendee ->
                                VisitorItem(
                                    initials = attendee.fullName.toInitials(),
                                    userId = attendee.userId,
                                    fullName = attendee.fullName,
                                    isCreator = eventScreenState.host == attendee.userId,
                                    onDeleteClicked = { userId ->
                                        eventScreenEvent(EventScreenEvent.OnDeleteVisitor(userId))
                                    }
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = modifier.height(26.dp))
                        AgendaAction(
                            agendaActionType = if (eventScreenState.isUserEventCreator) {
                                AgendaActionType.DELETE_EVENT
                            } else {
                                AgendaActionType.JOIN_EVENT
                            },
                            onActionClicked = { agendaActionType ->
                                when (agendaActionType) {
                                    AgendaActionType.DELETE_EVENT -> {
                                        eventScreenEvent(
                                            EventScreenEvent.OnShowDeleteEventAlertDialog(
                                                shouldShowDeleteAlertDialog = true
                                            )
                                        )
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
       //     }
        }
    )

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

                /** TODO Remove this logic for checking attendees already added to the viewModel */
                val attendee = eventScreenState.attendees.firstOrNull { attendee ->
                    attendee.email == visitorEmail
                }
                if(attendee == null) {
                    /** Avoid adding duplicate attendees - check if they have already been added */
                    eventScreenEvent(EventScreenEvent.CheckVisitorAlreadyAdded(false))
                    /** Check if the visitors email does exist */
                    eventScreenEvent(EventScreenEvent.CheckVisitorExists(visitorEmail))
                }
                else {
                    eventScreenEvent(EventScreenEvent.CheckVisitorAlreadyAdded(true))
                }
            },
            isEmailVerified = eventScreenState.isEmailVerified,
            isAlreadyAdded = eventScreenState.isAlreadyAdded,
            isLoading = eventScreenState.isVerifyingVisitorEmail
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
fun PreviewEventScreen() {
    BusbyTaskyTheme {
        EventScreen(
            eventScreenState = EventScreenState(),
            eventScreenEvent = {},
            modifier = Modifier.fillMaxWidth(),
            onEditDescriptionClicked = {},
            onEditTitleClicked = {},
            onCloseClicked = {}
        )
    }
}