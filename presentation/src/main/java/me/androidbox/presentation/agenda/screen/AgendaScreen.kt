package me.androidbox.presentation.agenda.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import me.androidbox.component.R
import me.androidbox.component.agenda.AgendaTopBar
import me.androidbox.component.general.TaskActionButton
import me.androidbox.component.ui.theme.agendaBackgroundColor
import me.androidbox.component.ui.theme.backgroundColor
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(
    agendaScreenState: AgendaScreenState,
    agendaScreenEvent: (AgendaScreenEvent) -> Unit,
    modifier: Modifier = Modifier) {

    CalendarDialog(
        state = agendaScreenState.calendarState,
        config = CalendarConfig(
            style = CalendarStyle.MONTH,
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { localDate ->
            agendaScreenEvent(AgendaScreenEvent.OnDateChanged(localDate))
        }
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundColor)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                initials = agendaScreenState.usersInitials,
                displayMonth = agendaScreenState.displayMonth,
                onProfileButtonClicked = {
                    /** TODO Open dropdown menu here */
                    Log.d("AGENDA_SCREEN", "Profile button clicked")
                },
                onDateClicked = {
                    agendaScreenState.calendarState.show()
                },
            )
        },
        floatingActionButton = {
            TaskActionButton(
                modifier =  Modifier
                    .size(56.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(size = 16.dp)
                    ),
                iconResource = R.drawable.add_white,
                onActionClicked = {
                    /* TODO Add dropdown menu to create new events, reminders, and tasks */
                    Log.d("AGENDA_SCREEN", "Action button clicked")
            })
        },
    ) { paddingValues ->

        LazyColumn(Modifier.padding(paddingValues)) {
            /*
            *
            * TODO Add content here for each of the agenda items
            *  i.e. Event, Reminders, and Tasks
            *
            * */
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaScreen() {
    BusbyTaskyTheme {
        AgendaScreen(
            agendaScreenState = AgendaScreenState(usersInitials = "SM"),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.agendaBackgroundColor),
            agendaScreenEvent = {}
        )
    }
}