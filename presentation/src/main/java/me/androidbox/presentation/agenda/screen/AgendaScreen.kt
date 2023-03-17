package me.androidbox.presentation.agenda.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import me.androidbox.component.agenda.AgendaTopBar
import me.androidbox.component.general.TaskActionButton
import me.androidbox.component.ui.theme.backgroundColor
import me.androidbox.component.R
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(
    agendaScreenState: State<AgendaScreenState>,
    agendaScreenEvent: (AgendaScreenEvent) -> Unit,
    modifier: Modifier = Modifier) {

    val calendarState = rememberUseCaseState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            style = CalendarStyle.MONTH,
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { localDate ->
            agendaScreenEvent(AgendaScreenEvent.OnDateChanged(localDate.month.toString()))
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
                initials = agendaScreenState.value.usersInitials,
                displayMonth = agendaScreenState.value.displayMonth,
                onProfileButtonClicked = {

                },
                onDateClicked = {
                    calendarState.show()
                },
            )
        },
        floatingActionButton = {
            TaskActionButton(
                iconResource = R.drawable.add_white,
                onActionClicked = {
                    /* TODO Open options menu */
            })
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {

        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaScreen() {
    BusbyTaskyTheme {
   /*     AgendaScreen(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.agendaBackgroundColor)
        )*/
    }
}
