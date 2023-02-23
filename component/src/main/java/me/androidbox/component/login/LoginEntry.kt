package me.androidbox.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.general.UserInputEntry
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@Composable
fun LoginEntry(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()) {
        UserInputEntry(
            inputValue = "",
            isInputValid = false,
            placeholderText = "name",
            onInputChange = { _ ->

            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        UserInputEntry(
            inputValue = "",
            isInputValid = false,
            placeholderText = "name",
            onInputChange = { _ ->

            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        UserInputEntry(
            inputValue = "",
            isInputValid = false,
            placeholderText = "name",
            onInputChange = { _ ->

            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailEntry() {
    BusbyTaskyTheme {
        LoginEntry()
    }
}