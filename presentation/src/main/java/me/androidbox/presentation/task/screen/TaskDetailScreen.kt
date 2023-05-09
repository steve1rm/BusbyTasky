package me.androidbox.presentation.task.screen

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.androidbox.component.agenda.AgendaDetailTopBar
import me.androidbox.component.agenda.AgendaDurationSimple
import me.androidbox.component.agenda.AgendaHeader
import me.androidbox.component.agenda.AgendaHeaderItem
import me.androidbox.component.agenda.AlarmReminder
import me.androidbox.component.agenda.AlarmReminderItem
import me.androidbox.component.agenda.EditModeType
import me.androidbox.component.general.AgendaDropDownMenu
import me.androidbox.component.general.AgendaSnackbar
import me.androidbox.component.ui.theme.Black
import me.androidbox.component.ui.theme.White
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.backgroundWhiteColor
import me.androidbox.component.ui.theme.dropDownMenuBackgroundColor
import me.androidbox.domain.DateTimeFormatterProvider.DATE_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.LONG_DATE_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.TIME_PATTERN
import me.androidbox.domain.DateTimeFormatterProvider.formatDateTime
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskDetailScreenState: TaskDetailScreenState,
    taskDetailScreenEvent: (taskDetailScreenEvent: TaskDetailScreenEvent) -> Unit,
    onEditTitleClicked: (title: String) -> Unit,
    onEditDescriptionClicked: (description: String) -> Unit,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val calendarStateTimeDate = rememberUseCaseState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    var snackbarJob: Job? = null

    LaunchedEffect(key1 = taskDetailScreenState.showSnackBar) {
        if(taskDetailScreenState.showSnackBar) {
            snackbarJob?.cancel()
            snackbarJob = coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = taskDetailScreenState.snackbarDisplayMessage,
                    actionLabel = taskDetailScreenState.snackbarActionMessage,
                    duration = SnackbarDuration.Short
                )
            }
            taskDetailScreenEvent(TaskDetailScreenEvent.OnShowSnackBar(showSnackBar = false))
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaDetailTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                    .padding(horizontal = 16.dp),
                editModeType = if(taskDetailScreenState.isEditMode) { EditModeType.SaveMode() } else { EditModeType.EditMode() },
                displayDate = taskDetailScreenState.from.formatDateTime(LONG_DATE_PATTERN),
                onCloseClicked = {
                    onCloseClicked()
                },
                onEditClicked = {
                    taskDetailScreenEvent(TaskDetailScreenEvent.OnEditModeChangeStatus(isEditMode = true))
                },
                onSaveClicked = {
                    taskDetailScreenEvent(TaskDetailScreenEvent.OnSaveTaskDetails(taskDetailScreenState.taskId))
                })
        },
        snackbarHost = {
            AgendaSnackbar(
                snackbarHostState = snackbarHostState,
                onAction = {

                }) {
            }
        }
        ) { paddingValues ->

        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .background(Black)) {

            Column (modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(
                    color = White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = modifier.height(30.dp))

                AgendaHeader(
                    agendaHeaderItem = AgendaHeaderItem.TASK,
                    subTitle = taskDetailScreenState.taskTitle,
                    description = taskDetailScreenState.taskDescription,
                    isEditMode = taskDetailScreenState.isEditMode,
                    onEditTitleClicked = { newTitle ->
                        onEditTitleClicked(newTitle)
                    },
                    onEditDescriptionClicked = { newDescription ->
                        onEditDescriptionClicked(newDescription)
                    }
                )

                Spacer(modifier = modifier.height(26.dp))

                AgendaDurationSimple(
                    isEditMode = taskDetailScreenState.isEditMode,
                    startTime = taskDetailScreenState.from.formatDateTime(TIME_PATTERN),
                    startDate = taskDetailScreenState.from.formatDateTime(DATE_PATTERN),
                    onStartDurationClicked = {
                        calendarStateTimeDate.show()
                    })

                AgendaDropDownMenu(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor),
                    shouldOpenDropdown = taskDetailScreenState.shouldOpenDropdown,
                    onCloseDropdown = {
                        taskDetailScreenEvent(
                            TaskDetailScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))
                    },
                    listOfMenuItemId = AlarmReminderItem.values().map { alarmReminderItem ->
                        alarmReminderItem.stringResId
                    },
                    onSelectedOption = { item ->
                        taskDetailScreenEvent(TaskDetailScreenEvent.OnAlarmReminderChanged(AlarmReminderItem.values()[item]))
                        taskDetailScreenEvent(TaskDetailScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = false))
                    }
                )

                AlarmReminder(
                    reminderText = stringResource(id = taskDetailScreenState.alarmReminderItem.stringResId),
                    isEditMode = taskDetailScreenState.isEditMode,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.backgroundWhiteColor),
                    onReminderClicked = {
                        taskDetailScreenEvent(TaskDetailScreenEvent.OnShowAlarmReminderDropdown(shouldOpen = true))
                    }
                )

                Spacer(modifier = modifier.height(26.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTaskDetailScreenEditMode() {
    BusbyTaskyTheme {
        TaskDetailScreen(
            taskDetailScreenState = TaskDetailScreenState(
                isEditMode = true,
                taskTitle = "This is the title",
                taskDescription = "This is the description of the task"
            ),
            taskDetailScreenEvent = {},
            onEditTitleClicked = {},
            onEditDescriptionClicked = {},
            onCloseClicked = {}
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewTaskDetailScreenNonEditMode() {
    BusbyTaskyTheme {
        TaskDetailScreen(
            taskDetailScreenState = TaskDetailScreenState(
                isEditMode = false,
                taskTitle = "This is the title",
                taskDescription = "This is the description of the task",
                from = ZonedDateTime.now(ZoneId.systemDefault())
            ),
            taskDetailScreenEvent = {},
            onEditTitleClicked = {},
            onEditDescriptionClicked = {},
            onCloseClicked = {}
        )
    }
}