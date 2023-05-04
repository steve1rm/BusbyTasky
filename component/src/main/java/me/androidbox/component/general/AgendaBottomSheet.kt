package me.androidbox.component.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.Gray
import me.androidbox.component.ui.theme.White
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.backgroundWhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaBottomSheet(
    listOfMenuItemId: List<Int>,
    onSelectedOption: (item: Int) -> Unit,
    onCloseDropdown: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit) {

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.backgroundWhiteColor,
        sheetState = sheetState,
        modifier = modifier,
        onDismissRequest = {
            onCloseDropdown()
        },
    ) {
        topBar()

        listOfMenuItemId.forEachIndexed { index, itemId, ->
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .clickable {
                        onSelectedOption(index)
                    },
                text = stringResource(id = itemId),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)

            if(index < listOfMenuItemId.count() - 1) {
                Divider(color = Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun PreviewAgendaBottomSheet() {
    BusbyTaskyTheme {
        AgendaBottomSheet(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.backgroundWhiteColor),
            listOfMenuItemId = listOf(
                R.string.ten_minutes_before,
                R.string.thirty_minutes_before,
                R.string.one_hour_before,
                R.string.six_hours_before,
                R.string.one_day_before
            ),
            sheetState = rememberModalBottomSheetState(),
            onCloseDropdown = {},
            onSelectedOption = {},
            topBar = {
                AgendaBottomSheetToolbar(
                    title = R.string.alarm_reminder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = White)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    onCloseClicked = {})
            }
        )
    }
}