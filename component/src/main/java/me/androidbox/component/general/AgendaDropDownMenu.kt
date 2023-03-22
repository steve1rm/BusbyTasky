package me.androidbox.component.general

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.divider
import me.androidbox.component.ui.theme.dropDownMenuBackgroundColor
import me.androidbox.component.ui.theme.dropDownMenuColor

@Composable
fun AgendaDropDownMenu(
    shouldOpenDropdown: Boolean,
    listOfMenuItemId: List<Int>,
    onSelectedOption: (item: Int) -> Unit,
    onCloseDropdown: () -> Unit,
    modifier: Modifier = Modifier,
) {

    DropdownMenu(
        modifier = modifier,
        expanded = shouldOpenDropdown,
        onDismissRequest = {
            onCloseDropdown()
        }
    ) {
        listOfMenuItemId.forEachIndexed { index, itemId, ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = itemId),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.dropDownMenuColor)
                },
                onClick = {
                    onSelectedOption(index)
                }
            )

            /* Avoid displaying a divider for the final item */
            if(index < listOfMenuItemId.lastIndex) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.divider,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaDropDownMenuAgenda() {
    BusbyTaskyTheme {
        AgendaDropDownMenu(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor),
            shouldOpenDropdown = true,
            onCloseDropdown = { },
            listOfMenuItemId = listOf(R.string.open, R.string.edit, R.string.delete),
            onSelectedOption = { }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaDropDownMenuReminder() {
    BusbyTaskyTheme {
        AgendaDropDownMenu(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.dropDownMenuBackgroundColor),
            shouldOpenDropdown = true,
            onCloseDropdown = { },
            listOfMenuItemId = listOf(
                R.string.ten_minutes_before,
                R.string.thirty_minutes_before,
                R.string.one_hour_before,
                R.string.six_hours_before,
                R.string.one_day_before
            ),
            onSelectedOption = { }
        )
    }
}
