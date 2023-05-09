package me.androidbox.presentation.agenda.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import me.androidbox.component.R
import me.androidbox.component.agenda.AgendaCard
import me.androidbox.component.agenda.AgendaCardType
import me.androidbox.component.agenda.AgendaDaySelector
import me.androidbox.component.agenda.AgendaTopBar
import me.androidbox.component.general.AgendaDropDownMenu
import me.androidbox.component.general.TaskActionButton
import me.androidbox.component.ui.theme.agendaBackgroundColor
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.dropDownMenuBackgroundColor
import me.androidbox.domain.DateTimeFormatterProvider.toDisplayDateTime
import me.androidbox.domain.DateTimeFormatterProvider.toZoneDateTime
import me.androidbox.domain.agenda.model.AgendaItem
import me.androidbox.domain.alarm_manager.AgendaType
import me.androidbox.presentation.agenda.constant.AgendaMenuActionType
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(
    agendaScreenState: AgendaScreenState,
    agendaScreenEvent: (AgendaScreenEvent) -> Unit,
    onSelectedEditAgendaItemClicked: (agendaItem: AgendaItem, agendaMenuActionType: AgendaMenuActionType) -> Unit,
    onSelectedAgendaItem: (agendaType: Int) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier) {

    val calendarState = rememberUseCaseState()
    val rememberSwipeRefreshState = rememberSwipeRefreshState(isRefreshing = agendaScreenState.isRefreshingAgenda)

    LaunchedEffect(key1 = agendaScreenState.deletedCacheCompleted) {
        if(agendaScreenState.deletedCacheCompleted) {
            onLogout()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                AgendaTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                    initials = agendaScreenState.usersInitials,
                    displayMonth = agendaScreenState.selectedDate.month.toString(),
                    onProfileButtonClicked = {
                        agendaScreenEvent(AgendaScreenEvent.OnOpenLogoutDropDownMenu(shouldOpen = true))
                    },
                    onDateClicked = {
                        calendarState.show()
                    },
                    dropDownMenu = {
                        AgendaDropDownMenu(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor)
                                .align(Alignment.BottomEnd),
                            shouldOpenDropdown = agendaScreenState.shouldOpenLogoutDropDownMenu,
                            onCloseDropdown = {
                                agendaScreenEvent(
                                    AgendaScreenEvent.OnOpenLogoutDropDownMenu(shouldOpen = false)
                                )
                            },
                            listOfMenuItemId = listOf(me.androidbox.presentation.R.string.logout),
                            onSelectedOption = { _ ->
                                agendaScreenEvent(AgendaScreenEvent.OnOpenLogoutDropDownMenu(shouldOpen = false))
                                agendaScreenEvent(AgendaScreenEvent.OnLogoutClicked)
                            }
                        )
                    }
                )
           }
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
                        agendaScreenEvent(AgendaScreenEvent.OnChangedShowDropdownStatus(shouldOpen = true))
                    })

                AgendaDropDownMenu(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor)
                        .align(Alignment.BottomEnd),
                    shouldOpenDropdown = agendaScreenState.shouldOpenDropdown,
                    onCloseDropdown = {
                        agendaScreenEvent(
                            AgendaScreenEvent.OnChangedShowDropdownStatus(shouldOpen = false))
                    },
                    listOfMenuItemId = listOf(R.string.event, R.string.task, R.string.reminder),
                    onSelectedOption = { item ->
                        onSelectedAgendaItem(item)
                        agendaScreenEvent(AgendaScreenEvent.OnChangedShowDropdownStatus(shouldOpen = false))
                    }
                )
            }
        },
    )
    { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            SwipeRefresh(
                state = rememberSwipeRefreshState,
                onRefresh = {
                    agendaScreenEvent(AgendaScreenEvent.OnSwipeToRefreshAgenda(agendaScreenState.selectedDate))
                }
            ) {

            LazyColumn(
                Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    AgendaDaySelector(
                        date = agendaScreenState.selectedDate,
                        isSelectedDay = agendaScreenState.selectedDay,
                        onSelected = { day ->
                            agendaScreenEvent(AgendaScreenEvent.OnSelectedDayChanged(day))
                        })
                }

                items(agendaScreenState.agendaItems) { agendaItem ->
                        AgendaCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(shape = RoundedCornerShape(22.dp)),
                            title = agendaItem.title,
                            subtitle = agendaItem.description,
                            dateTimeInfo = agendaItem.toDisplayDateTime(),
                            agendaCardType = when (agendaItem.agendaType) {
                                AgendaType.EVENT -> AgendaCardType.EVENT
                                AgendaType.TASK -> AgendaCardType.TASK
                                AgendaType.REMINDER -> AgendaCardType.REMINDER
                            },
                            isAgendaCompleted = false,
                            onMenuOptionClicked = {
                                println("Event ${agendaItem.id} has been clicked")
                                agendaScreenEvent(AgendaScreenEvent.OnAgendaItemClicked(agendaItem))
                                agendaScreenEvent(
                                    AgendaScreenEvent.OnChangeShowEditAgendaItemDropdownStatus(
                                        shouldOpen = true,
                                        agendaItem = agendaItem
                                    )
                                )
                            },
                            dropDownMenu = {
                                /** Open, Edit, Delete Agenda Items */
                                AgendaDropDownMenu(
                                    modifier = Modifier
                                        .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor)
                                        .align(Alignment.BottomEnd),
                                    shouldOpenDropdown = agendaItem == agendaScreenState.agendaItemWithOperationBeingSelected && agendaScreenState.shouldOpenEditAgendaDropdown,
                                    onCloseDropdown = {
                                        agendaScreenEvent(
                                            AgendaScreenEvent.OnChangeShowEditAgendaItemDropdownStatus(
                                                shouldOpen = false,
                                                agendaItem = agendaItem
                                            )
                                        )
                                    },
                                    listOfMenuItemId = AgendaMenuActionType.values()
                                        .map { it.titleId },
                                    onSelectedOption = { item ->
                                        agendaScreenState.agendaItemClicked?.let { agendaItem ->
                                            onSelectedEditAgendaItemClicked(
                                                agendaItem,
                                                AgendaMenuActionType.values()[item]
                                            )
                                            agendaScreenEvent(
                                                AgendaScreenEvent.OnChangeShowEditAgendaItemDropdownStatus(
                                                    shouldOpen = false,
                                                    agendaItem = agendaItem
                                                )
                                            )
                                        }
                                    }
                                )
                            }
                        )
                }
            }
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
            agendaScreenEvent(AgendaScreenEvent.OnDateChanged(localDate.toZoneDateTime()))
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaScreen() {
    BusbyTaskyTheme {
        AgendaScreen(
            agendaScreenState = AgendaScreenState(
                usersInitials = "SM",
                shouldOpenLogoutDropDownMenu = true,
                shouldOpenDropdown = true),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.agendaBackgroundColor),
            agendaScreenEvent = {},
            onSelectedEditAgendaItemClicked = { _, _ -> },
            onSelectedAgendaItem = {},
            onLogout = {}
        )
    }
}
