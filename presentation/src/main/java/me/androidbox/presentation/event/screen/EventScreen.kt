package me.androidbox.presentation.event.screen

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.agenda.*
import me.androidbox.component.general.AgendaDropDownMenu
import me.androidbox.component.general.PhotoPicker
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.backgroundWhiteColor
import me.androidbox.component.ui.theme.dropDownMenuBackgroundColor
import me.androidbox.domain.alarm_manager.AlarmItem
import org.threeten.bp.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    eventScreenState: EventScreenState,
    eventScreenEvent: (EventScreenEvent) -> Unit,
    onEditTitleClicked: (title: String) -> Unit,
    onEditDescriptionClicked: (description: String) -> Unit,
    modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaDetailTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                    .padding(horizontal = 16.dp),
                editModeType = EditModeType.EditMode(),
                displayDate = "31 March 2023", /* TODO Get the current date from the zoneDateTime and format it */
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
                        startTime = "08:00",
                        endTime = "08:30",
                        startDate = "Jul 21 2022",
                        endDate = "Jul 21 2022",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = modifier.height(26.dp))
                    AgendaDropDownMenu(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor),
                        shouldOpenDropdown = eventScreenState.shouldOpenDropdown,
                        onCloseDropdown = {
                            eventScreenEvent(
                                EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))
                        },
                        listOfMenuItemId = listOf(
                            R.string.ten_minutes_before,
                            R.string.thirty_minutes_before,
                            R.string.one_hour_before,
                            R.string.six_hours_before,
                            R.string.one_day_before),
                        onSelectedOption = { item ->
                            eventScreenEvent(EventScreenEvent.OnAlarmReminderTextChanged(
                                AlarmReminderItem.values()[item].text))

                            eventScreenEvent(EventScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))

                            /** TODO Using mock data to set the Alarm Monitor, here use the eventScreenState start time date */
                            val alarmItem = AlarmItem(
                                ZonedDateTime.now().plusSeconds(30L),
                                "THIS IS THE MESSAGE FROM THE ALARM REMINDER"
                            )
                            eventScreenEvent(EventScreenEvent.OnScheduleAlarmReminder(alarmItem))
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
}

enum class AlarmReminderItem(@StringRes val text: Int) {
    TEN_MINUTES(R.string.ten_minutes_before),
    THIRTY_MINUTES(R.string.thirty_minutes_before),
    ONE_HOUR(R.string.one_hour_before),
    SIX_HOUR(R.string.six_hours_before),
    ONE_DAY(R.string.one_day_before)
}

fun onSelectedEventReminderItem(item: Int, eventScreenState: EventScreenState) {
    Log.d("EVENT", "ITEM [ $item ]")
    /** TODO Set the Alarm Monitor here use the eventScreenState start and end duration */

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