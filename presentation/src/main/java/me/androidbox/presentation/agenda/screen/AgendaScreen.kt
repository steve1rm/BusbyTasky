package me.androidbox.presentation.agenda.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.agenda.AgendaTopBar
import me.androidbox.component.general.TaskActionButton
import me.androidbox.component.ui.theme.backgroundColor
import me.androidbox.component.R
import me.androidbox.component.ui.theme.agendaBackgroundColor
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(
    modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundColor)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                initials = "SM",
                displayMonth = "December",
                onProfileButtonClicked = {

                },
                onCalendarClicked = {

                },
                onDateChanged = {

                }
            )
        },
        floatingActionButton = {
            TaskActionButton(
                iconResource = R.drawable.add_white) {
            }
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
        AgendaScreen(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.agendaBackgroundColor)
        )
    }
}