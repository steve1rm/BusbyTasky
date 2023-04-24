package me.androidbox.component.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.general.CalendarDayButton
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.Orange
import java.time.ZonedDateTime

@Composable
fun AgendaDaySelector(
    date: ZonedDateTime = ZonedDateTime.now(),
    isSelectedDay: ZonedDateTime = ZonedDateTime.now(),
    modifier: Modifier = Modifier,
    onSelected: (ZonedDateTime) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly) {

        (0L..6L).forEach { day ->
            CalendarDayButton(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(100.dp))
                    .background(color = Orange)
                    .size(width = 40.dp, height = 60.dp),
                date = date.plusDays(day),
                isSelected = isSelectedDay.dayOfWeek == date.plusDays(day).dayOfWeek,
                onSelected = { date ->
                    onSelected(date)
                }
            )
        }
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

