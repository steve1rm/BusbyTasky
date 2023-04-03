package me.androidbox.presentation.event.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import me.androidbox.component.agenda.*
import me.androidbox.component.general.AgendaDropDownMenu
import me.androidbox.component.general.PhotoPicker
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.backgroundWhiteColor
import me.androidbox.component.ui.theme.dropDownMenuBackgroundColor
import me.androidbox.domain.DateTimeFormatterProvider.DATE_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.TIME_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.formatDateTime
import me.androidbox.domain.alarm_manager.AlarmItem
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
    modifier: Modifier = Modifier) {

    val calendarStateTimeDate = rememberUseCaseState()

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaDetailTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                    .padding(horizontal = 16.dp),
                editModeType = EditModeType.EditMode(),
                displayDate = "31 March 2023", /* TODO Get the date from the agenda screen that was selected by the user */
                onCloseClicked = {  },
                onEditClicked = {  },
                onSaveClicked = {  })

        },
        content = {
            Column(modifier = Modifier
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
                        isEditMode = true,
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
                            eventScreenEvent(EventScreenEvent.OnPhotoUriAdded(uri))
                        }
                    )

                    Spacer(modifier = modifier.height(26.dp))
                    AgendaDuration(
                        isEditMode = true,
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

                    val context: Context = LocalContext.current

                    AgendaDropDownMenu(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor),
                        shouldOpenDropdown = eventScreenState.shouldOpenDropdown,
                        onCloseDropdown = {
                            eventScreenEvent(
                                EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))
                        },
                        listOfMenuItemId = AlarmReminderItem.values().map { alarmReminderItem ->
                            alarmReminderItem.stringResId
                        },
                        onSelectedOption = { item ->
                            eventScreenEvent(EventScreenEvent.OnAlarmReminderTextChanged(
                                AlarmReminderItem.values()[item].stringResId))

                            eventScreenEvent(EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))

                            setupAlarmReminderItem(eventScreenState = eventScreenState, item = item, context)?.let { alarmItem ->
                                eventScreenEvent(EventScreenEvent.OnScheduleAlarmReminder(alarmItem))
                            }
                        }
                    )

                    AlarmReminder(
                        reminderText = stringResource(id = eventScreenState.alarmReminderText),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.backgroundWhiteColor),
                        onReminderClicked = {
                            eventScreenEvent(EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = true))
                        }
                    )
                    Spacer(modifier = modifier.height(26.dp))

                    VisitorFilter(
                        modifier = Modifier.fillMaxWidth(),
                        selectedVisitorType = VisitorType.ALL,
                        onSelectedTypeClicked = {}
                    )


                    Spacer(modifier = modifier.height(26.dp))

                    AgendaAction(
                        agendaActionType = AgendaActionType.LEAVE_EVENT,
                        onActionClicked = {}
                    )
                }
            }
        }
    )

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
}
private fun setupAlarmReminderItem(eventScreenState: EventScreenState, item: Int, context: Context): AlarmItem? {
    /*
       check what reminder that user has selected.
       If user has set the following 10 minutes, 30 minutes, 1 hour, 6 hours. Set the alarm to go off before selected time
       If user has set the following 1 day then use the date to set the alarm 1 day before
    **/
    return when(item) {
        in 0..3 -> {
            val startTime = eventScreenState.startTime

            AlarmItem(
                dateTime = startTime.minusMinutes(startTime.toEpochSecond()),
                message = context.getString(eventScreenState.alarmReminderText)
            )
        }
        4 -> {
            val startDate = eventScreenState.startDate

            AlarmItem(
                dateTime = startDate.minusDays(startDate.toEpochSecond()),
                message = context.getString(eventScreenState.alarmReminderText)
            )
        }
        else -> {
            null
        }
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
        )
    }
}