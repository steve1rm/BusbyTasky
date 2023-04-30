package me.androidbox.component.agenda

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.backgroundWhiteColor
import java.time.ZonedDateTime

@Composable
fun AlarmReminder(
    reminderText: String,
    onReminderClicked: () -> Unit,
    modifier: Modifier = Modifier) {

        Row(modifier = modifier.clickable {
            onReminderClicked()
        },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                Icon(painter = painterResource(id = R.drawable.bell), contentDescription = "bell")

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = reminderText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.agendaBodyTextColor)
                    )
            }

            Row {
                Icon(painter = painterResource(id = R.drawable.forward_arrow), contentDescription = "forward arrow")
            }
        }
}

enum class AlarmReminderItem(@StringRes val stringResId: Int) {
    TEN_MINUTES(R.string.ten_minutes_before),
    THIRTY_MINUTES(R.string.thirty_minutes_before),
    ONE_HOUR(R.string.one_hour_before),
    SIX_HOUR(R.string.six_hours_before),
    ONE_DAY(R.string.one_day_before)
}

@Composable
@Preview(showBackground = true, name = "Alarm reminder")
fun PreviewAlarmReminder() {
    BusbyTaskyTheme {
        AlarmReminder(
            reminderText = "30 minutes before",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.backgroundWhiteColor),
            onReminderClicked = {}
        )
    }
}

