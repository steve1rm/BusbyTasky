package me.androidbox.component.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun AgendaDuration(
    isEditMode: Boolean,
    startTime: String,
    endTime: String,
    startDate: String,
    endDate: String,
    onStartDurationClicked: () -> Unit,
    onEndDurationClicked: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = stringResource(R.string.from_duration).format(startTime),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.agendaBodyTextColor
                ))

            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = startDate,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.agendaBodyTextColor)
            )

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

        Spacer(modifier = Modifier.height(28.dp))

        Divider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        Spacer(modifier = Modifier.height(28.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = stringResource(id = R.string.to_duration).format(endTime),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.agendaBodyTextColor)
            )

            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = endDate,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.agendaBodyTextColor)
            )

            if(isEditMode) {
                IconButton(onClick = {
                    onEndDurationClicked()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.forward_arrow),
                        contentDescription = stringResource(R.string.forward_arrow),
                        tint = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
@Preview(showBackground = true, name = "Shows the duraton of time and day")
fun PreviewAgendaDuration() {
    BusbyTaskyTheme {
        AgendaDuration(
            isEditMode = true,
            startTime = "08:00",
            endTime = "08:30",
            startDate = "Jul 21 2022",
            endDate = "Jul 21 2022",
            modifier = Modifier.fillMaxWidth(),
            onStartDurationClicked = {},
            onEndDurationClicked = {}
        )
    }
}

