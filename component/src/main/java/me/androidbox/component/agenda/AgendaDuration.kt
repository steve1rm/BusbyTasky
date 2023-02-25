package me.androidbox.component.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun AgendaDuration(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = stringResource(R.string.from_duration).format("08:00"),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.agendaBodyTextColor)

            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = "Jul 21 2022",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Divider(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        Spacer(modifier = Modifier.height(28.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = stringResource(id = R.string.to_duration).format("08:30"),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)

            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = "Jul 21 2022",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Divider(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)
    }
}

@Composable
@Preview(showBackground = true, name = "Shows the duraton of time and day")
fun PreviewAgendaDuration() {
    BusbyTaskyTheme {
        AgendaDuration()
    }
}

