package me.androidbox.component.general

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.androidbox.component.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaBottomSheet(
    listOfMenuItemId: List<Int>,
    onSelectedOption: (item: Int) -> Unit,
    onCloseDropdown: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier) {

    ModalBottomSheet(
        sheetState = sheetState,
        modifier = modifier,
        onDismissRequest = {
            onCloseDropdown()
        },
    ) {

        listOfMenuItemId.forEachIndexed { index, itemId, ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = itemId),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = White)
                },
                onClick = {
                    onSelectedOption(index)
                }
            )
        }
    }
}