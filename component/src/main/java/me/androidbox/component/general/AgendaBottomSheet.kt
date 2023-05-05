package me.androidbox.component.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.White
import me.androidbox.component.ui.theme.backgroundWhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaBottomSheet(
    onCloseDropdown: () -> Unit,
    bottomSheetState: SheetState,
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier) {

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.backgroundWhiteColor,
        sheetState = bottomSheetState,
        modifier = modifier,
        onDismissRequest = {
            onCloseDropdown()
        },
    ) {
        topBar()
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun PreviewAgendaBottomSheet() {
    BusbyTaskyTheme {
        AgendaBottomSheet(
            modifier = Modifier,
            bottomSheetState = rememberModalBottomSheetState(),
            onCloseDropdown = {},
            topBar = {
                AgendaBottomSheetToolbar(
                    title = R.string.alarm_reminder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = White)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    onCloseClicked = {})
            },
            content = {
                AlarmReminderOptions(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    listOfMenuItemId = listOf(
                        R.string.ten_minutes_before,
                        R.string.thirty_minutes_before,
                        R.string.one_hour_before,
                        R.string.six_hours_before,
                        R.string.one_day_before),
                    onSelectedOption = {}
                )
            }
        )
    }
}