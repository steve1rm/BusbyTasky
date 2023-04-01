package me.androidbox.presentation.event.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import me.androidbox.component.agenda.*
import me.androidbox.component.general.PhotoPicker
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.backgroundWhiteColor
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    eventScreenState: EventScreenState,
    eventScreenEvent: (EventScreenEvent) -> Unit,
    onEditTitleClicked: (title: String) -> Unit,
    onEditDescriptionClicked: (description: String) -> Unit,
    modifier: Modifier = Modifier) {

    val calendarStateTime = rememberUseCaseState()
    val calendarStateDate = rememberUseCaseState()

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
                        isEditMode = true,
                        startTime = timeFormatter.format(eventScreenState.startTimeDuration),
                        endTime = timeFormatter.format(eventScreenState.endTimeDuration),
                        startDate = dateFormatter.format(eventScreenState.startDateDuration),
                        endDate = dateFormatter.format(eventScreenState.endDateDuration),
                        modifier = Modifier.fillMaxWidth(),
                        onStartDurationClicked = {
                            calendarStateTime.show()
                        },
                        onEndDurationClicked = {
                            calendarStateDate.show()
                        }
                    )

                    Spacer(modifier = modifier.height(26.dp))

                    AlarmReminder(
                        reminderText = "30 minutes before",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.backgroundWhiteColor)
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

    CalendarDialog(
        state = calendarStateDate,
        config = CalendarConfig(
            style = CalendarStyle.MONTH,
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { localDate ->
            Log.d("EVENT_SCREEN", "${dateFormatter.format(localDate)}")
         //   agendaScreenEvent(AgendaScreenEvent.OnDateChanged(localDate))
        }
    )

    DateTimeDialog(
        state = calendarStateTime,
        selection = DateTimeSelection.Time { localTime ->
            localTime.toEpochSecond(localTime, ZoneOffset.UTC)

            Log.d("EVENT_SCREEN", "${timeFormatter.format(localTime)}")
            //   agendaScreenEvent(AgendaScreenEvent.OnDateChanged(localDate))
        }
    )

}

val dateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.getDefault())
val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

@Composable
@Preview(showBackground = true)
fun PreviewEventScreen() {
    BusbyTaskyTheme {
        EventScreen(
            eventScreenState = EventScreenState(),
            eventScreenEvent = {},
            modifier = Modifier.fillMaxWidth(),
            onEditDescriptionClicked = {},
            onEditTitleClicked = {}
        )
    }
}