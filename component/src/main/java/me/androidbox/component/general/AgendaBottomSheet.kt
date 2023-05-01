package me.androidbox.component.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AgendaBottomSheet(
    toolBar: @Composable (onCloseClicked: () -> Unit) -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val modelBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded
    )

    val rememberCoroutineScope = rememberCoroutineScope()
    val hideModalBottomSheet: () -> Unit = {
        rememberCoroutineScope.launch {
            if (modelBottomSheetState.isVisible) {
                modelBottomSheetState.hide()
            }
        }
    }
    ModalBottomSheetLayout(
        sheetState = modelBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            Column {
                toolBar(hideModalBottomSheet)
                content()
            }
        }
    ) {}
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaBottomSheet() {
    BusbyTaskyTheme {
        AgendaBottomSheet(
            toolBar = {
                onCloseClicked: () -> Unit -> Toolbar(title = "Select a reminder option", onCloseClicked = {})
            },
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "This is option 1")
                    Text(text = "This is option 2")
                    Text(text = "This is option 3")
                    Text(text = "This is option 4")
                    Text(text = "This is option 5")
                }
            }
        )
    }
}

