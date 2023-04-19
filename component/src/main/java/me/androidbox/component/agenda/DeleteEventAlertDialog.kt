package me.androidbox.component.agenda

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@Composable
fun DeleteEventAlertDialog(
    onConfirmationClicked: () -> Unit,
    onDismissClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissClicked()
        },
    title = {
        Text(text = stringResource(id = R.string.delete_event))
    },
    text = {
        Text(text = stringResource(R.string.confirm_delete_event))
    },
    confirmButton = {
        Button(onClick = { onConfirmationClicked() }) {
            Text(text = "Confirm")
        }
    },
    dismissButton = {
        Button(onClick = { onDismissClicked() }) {
            Text(text = "Dismiss")
        }
    })
}

@Composable
@Preview(showBackground = true)
fun PreviewDeleteEventAlertDialog() {
    BusbyTaskyTheme {
        DeleteEventAlertDialog(
            onConfirmationClicked = {},
            onDismissClicked = {}
        )
    }
}