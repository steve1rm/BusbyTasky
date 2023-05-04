package me.androidbox.component.agenda

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.backgroundWhiteColor

@Composable
fun AlarmReminder(
    reminderText: String,
    onReminderClicked: () -> Unit,
    isEditMode: Boolean,
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

        if(isEditMode) {
            Row {
                IconButton(onClick = {
                    onReminderClicked()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.forward_arrow),
                        contentDescription = stringResource(R.string.forward_arrow),
                        tint = Color.Black
                    )
                }
            }
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
            isEditMode = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.backgroundWhiteColor),
            onReminderClicked = {}
        )
    }
}

