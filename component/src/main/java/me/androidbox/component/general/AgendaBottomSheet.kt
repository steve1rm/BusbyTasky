package me.androidbox.component.general

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundWhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaBottomSheet(
    shouldOpenBottomSheet: Boolean,
    onCloseDropdown: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier) {

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetScope = rememberCoroutineScope()

    if(shouldOpenBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.backgroundWhiteColor,
            sheetState = bottomSheetState,
            modifier = modifier,
            onDismissRequest = {
                bottomSheetScope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    if(!bottomSheetState.isVisible) {
                        onCloseDropdown()
                    }
                }
            },
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun PreviewAgendaBottomSheet() {
    BusbyTaskyTheme {
        AgendaBottomSheet(
            modifier = Modifier,
            shouldOpenBottomSheet = false,
            onCloseDropdown = {},
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