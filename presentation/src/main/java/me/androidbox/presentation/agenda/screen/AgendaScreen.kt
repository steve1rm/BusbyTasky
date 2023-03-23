package me.androidbox.presentation.agenda.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import me.androidbox.component.R
import me.androidbox.component.agenda.AgendaTopBar
import me.androidbox.component.general.AgendaDropDownMenu
import me.androidbox.component.general.TaskActionButton
import me.androidbox.component.ui.theme.agendaBackgroundColor
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.dropDownMenuBackgroundColor
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(
    agendaScreenState: AgendaScreenState,
    agendaScreenEvent: (AgendaScreenEvent) -> Unit,
    modifier: Modifier = Modifier) {
    val calendarState = rememberUseCaseState()

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                initials = agendaScreenState.usersInitials,
                displayMonth = agendaScreenState.displayMonth,
                onProfileButtonClicked = {
                    /** TODO Open dropdown menu here */
                    Log.d("AGENDA_SCREEN", "Profile button clicked")
                },
                onDateClicked = {
                    calendarState.show()
                },
            )
        },
        floatingActionButton = {
            Box {
                TaskActionButton(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(size = 16.dp)
                        ),
                    iconResource = R.drawable.add_white,
                    onActionClicked = {
                        agendaScreenEvent(AgendaScreenEvent.OnShowDropdown(shouldOpen = true))
                    })

                AgendaDropDownMenu(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor)
                        .align(Alignment.BottomEnd),
                    shouldOpenDropdown = agendaScreenState.shouldOpenDropdown,
                    onCloseDropdown = {
                        agendaScreenEvent(
                            AgendaScreenEvent.OnShowDropdown(shouldOpen = false))
                    },
                    listOfMenuItemId = listOf(R.string.open, R.string.edit, R.string.delete),
                    onSelectedOption = { item ->
                        Log.d("AGENDA", "ITEM [ $item ]")
                    }
                )
            }
        },
    )
    { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            LazyColumn(Modifier.fillMaxWidth()) {
            /*
            *
            * TODO Add content here for each of the agenda items
            *  i.e. Event, Reminders, and Tasks
            *
            * */
            }
        }
    }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            style = CalendarStyle.MONTH,
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { localDate ->
            agendaScreenEvent(AgendaScreenEvent.OnDateChanged(localDate))
        }
    )
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
