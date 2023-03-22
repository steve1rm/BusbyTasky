package me.androidbox.component.agenda

import androidx.compose.foundation.background
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

@Composable
fun AlarmReminder(
    reminderText: String,
    modifier: Modifier = Modifier) {
        Row(modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                Icon(painter = painterResource(id = R.drawable.bell), contentDescription = "bell")

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = reminderText,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.agendaBodyTextColor)
            }

            Row {
                Icon(painter = painterResource(id = R.drawable.forward_arrow), contentDescription = "forward arrow")
            }
        }
}

@Composable
@Preview(showBackground = true, name = "Alarm reminder")
fun PreviewAlarmReminder() {
    BusbyTaskyTheme {
        AlarmReminder(
            reminderText = "30 minutes before",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.backgroundWhiteColor)
        )
    }
}

