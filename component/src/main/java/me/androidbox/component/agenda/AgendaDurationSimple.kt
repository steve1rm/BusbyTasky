package me.androidbox.component.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.divider

@Composable
fun AgendaDurationSimple(
    isEditMode: Boolean,
    startTime: String,
    startDate: String,
    onStartDurationClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = stringResource(R.string.from_duration).format(startTime),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)

            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = startDate,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)

            if(isEditMode) {
                IconButton(onClick = {
                    onStartDurationClicked()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.forward_arrow),
                        contentDescription = stringResource(R.string.forward_arrow),
                        tint = Color.Black
                    )
                }
            }
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
@Preview(showBackground = true, name = "Shows the duration of time and day")
fun PreviewAgendaDurationSimple() {
    BusbyTaskyTheme {
        AgendaDurationSimple(
            isEditMode = true,
            startTime = "08:00",
            startDate = "08:30",
            modifier = Modifier.fillMaxWidth(),
            onStartDurationClicked = {}
        )
    }
}

