package me.androidbox.component.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
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
import me.androidbox.component.ui.theme.divider

@Composable
fun AlarmReminder(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background)) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(4F)) {
                Icon(painter = painterResource(id = R.drawable.bell), contentDescription = "bell")

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "30 minutes before",
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.agendaBodyTextColor)
            }

            Row(modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End) {
                Icon(painter = painterResource(id = R.drawable.forward_arrow), contentDescription = "forward arrow")
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Divider(
            modifier.fillMaxWidth().padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)
    }
}

@Composable
@Preview(showBackground = true, name = "Alarm reminder")
fun PreviewAlarmReminder() {
    BusbyTaskyTheme {
        AlarmReminder()
    }
}

