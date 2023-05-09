package me.androidbox.component.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.Gray
import me.androidbox.component.ui.theme.agendaBodyTextColor

@Composable
fun AlarmReminderOptions(
    title: String,
    listOfMenuItemId: List<Int>,
    onSelectedOption: (item: Int) -> Unit,
    modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor
            )
        }

        itemsIndexed(listOfMenuItemId) { index,  itemId ->
            Text(
                modifier = modifier
                    .clickable {
                        onSelectedOption(index)
                    },
                text = stringResource(id = itemId),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)

            if(index < listOfMenuItemId.lastIndex) {
                Divider(color = Gray)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAlarmReminderOptions() {
    BusbyTaskyTheme {
        AlarmReminderOptions(
            title = stringResource(id = R.string.alarm_reminder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            listOfMenuItemId = listOf(
                R.string.ten_minutes_before,
                R.string.thirty_minutes_before,
                R.string.one_hour_before,
                R.string.six_hours_before,
                R.string.one_day_before
            ),
            onSelectedOption = {},
        )
    }
}


