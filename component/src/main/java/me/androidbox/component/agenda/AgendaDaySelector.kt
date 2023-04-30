package me.androidbox.component.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.CalendarDayButton
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import java.time.ZonedDateTime

@Composable
fun AgendaDaySelector(
    date: ZonedDateTime = ZonedDateTime.now(),
    isSelectedDay: ZonedDateTime = ZonedDateTime.now(),
    modifier: Modifier = Modifier,
    onSelected: (ZonedDateTime) -> Unit,
) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {

            /* Display only 6 days */
            (0L..6L).forEach { day ->
                CalendarDayButton(
                    modifier = Modifier
                        .size(width = 40.dp, height = 60.dp),
                    date = date.plusDays(day),
                    isSelected = isSelectedDay.dayOfWeek == date.plusDays(day).dayOfWeek,
                    onSelected = { date ->
                        onSelected(date)
                    }
                )
            }
        }

        Spacer(Modifier.height(34.dp))
        val selectedDate = if(isSelectedDay.dayOfWeek == ZonedDateTime.now().dayOfWeek) stringResource(R.string.today) else "${isSelectedDay.dayOfMonth} ${isSelectedDay.month} ${isSelectedDay.year}"
        Text(text = selectedDate, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaDaySelector() {
    BusbyTaskyTheme {
        var selectedDay by remember {
            mutableStateOf(ZonedDateTime.now())
        }

        AgendaDaySelector(
            date = ZonedDateTime.now(),
            isSelectedDay = selectedDay,
            modifier = Modifier.fillMaxWidth(),
            onSelected = {
                selectedDay = it
            }
        )
    }
}

