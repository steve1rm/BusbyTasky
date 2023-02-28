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
fun AgendaDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean> = mutableStateOf(false),
    onOpenClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
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
                    text = stringResource(R.string.open),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onOpenClicked()
            }
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        DropdownMenuItem(
            text = {
                Text(text = stringResource(R.string.edit),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onEditClicked()
            }
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp)

        DropdownMenuItem(
            text = {
                Text(text = stringResource(R.string.delete),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.dropDownMenuColor)
            },
            onClick = {
                onDeleteClicked()
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaDropDownMenu() {
    BusbyTaskyTheme {

        val isExpanded = remember {
            mutableStateOf(true)
        }

        AgendaDropDownMenu(
            isExpanded = isExpanded,
            onOpenClicked  = { },
            onEditClicked = { },
            onDeleteClicked = { }
        )
    }
}