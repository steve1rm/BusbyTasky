package me.androidbox.component.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.*

@Composable
fun AgendaReminderDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean> = mutableStateOf(false),
    onTenMinuteClicked: () -> Unit,
    onThirtyMinutesClicked: () -> Unit,
    onSixHoursClicked: () -> Unit,
    onOneHourClicked: () -> Unit,
    onOneDayBefore: () -> Unit
) {

    DropdownMenu(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor),
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        }
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.ten_minutes_before),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onTenMinuteClicked()
            }
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        DropdownMenuItem(
            text = {
                Text(text = stringResource(R.string.thirty_minutes_before),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onThirtyMinutesClicked()
            }
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        DropdownMenuItem(
            text = {
                Text(text = stringResource(R.string.one_hour_before),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onOneHourClicked()
            }
        )

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.six_hours_before),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onSixHoursClicked()
            }
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.one_day_before),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onOneDayBefore()
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaReminderDropDownMenu() {
    BusbyTaskyTheme {
        val isExpanded = remember {
            mutableStateOf(true)
        }

        AgendaReminderDropDownMenu(
            isExpanded = isExpanded,
            onTenMinuteClicked = {},
            onThirtyMinutesClicked = {},
            onSixHoursClicked = {},
            onOneHourClicked = {},
            onOneDayBefore = {}
        )
    }
}