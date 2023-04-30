package me.androidbox.component.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.DarkGray
import me.androidbox.component.ui.theme.Gray
import me.androidbox.component.ui.theme.Orange
import me.androidbox.component.ui.theme.White
import java.time.ZonedDateTime

@Composable
fun CalendarDayButton(
    date: ZonedDateTime,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onSelected: (date: ZonedDateTime) -> Unit) {

    val backgroundColor = if(isSelected) Orange else White
    val fontDayColor = if(isSelected) DarkGray else Gray

    Column(modifier = modifier
        .clip(shape = RoundedCornerShape(100.dp))
        .background(color = backgroundColor)
        .clickable {
            onSelected(date)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = date.dayOfWeek.name.first().toString(),
            style = MaterialTheme.typography.labelMedium.copy(
                color = fontDayColor
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.labelLarge.copy(
                color = DarkGray
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDayDateButtonSelected() {
    BusbyTaskyTheme {
        CalendarDayButton(
            date = ZonedDateTime.now(),
            isSelected = true,
            modifier = Modifier
                .background(color = Orange, shape = RoundedCornerShape(100.dp))
                .size(width = 40.dp, height = 60.dp),
            onSelected = {
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDayDateButtonUnselected() {
    BusbyTaskyTheme {
        CalendarDayButton(
            date = ZonedDateTime.now(),
            isSelected = false,
            modifier = Modifier
                .background(color = Orange, shape = RoundedCornerShape(100.dp))
                .size(width = 40.dp, height = 60.dp),
            onSelected = {
            }
        )
    }
}


