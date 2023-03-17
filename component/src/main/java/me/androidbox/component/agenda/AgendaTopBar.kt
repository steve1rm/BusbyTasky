package me.androidbox.component.agenda

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundColor
import me.androidbox.component.ui.theme.topbarButtonBackgroundColor
import me.androidbox.component.ui.theme.topbarFontColor
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaTopBar(
    initials: String,
    onProfileButtonClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
    onDateChanged: (localDate: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val calendarState = rememberUseCaseState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            style = CalendarStyle.MONTH,
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { localDate ->
            onDateChanged(localDate)
        }
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        Text(
            modifier = Modifier.clickable {
                onCalendarClicked()
            },
            text = "March",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.topbarFontColor
        )

        Button(
            modifier = Modifier.size(34.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.topbarButtonBackgroundColor,
                contentColor = MaterialTheme.colorScheme.topbarFontColor),
            onClick = {
                onProfileButtonClicked()
            }) {
            Text(
                maxLines = 1,
                text = initials,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaTopBar() {
    BusbyTaskyTheme {
        AgendaTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.backgroundColor)
                .padding(horizontal = 16.dp),
            initials = "SM",
            onCalendarClicked = {},
            onProfileButtonClicked = {},
            onDateChanged = {}
        )
    }
}