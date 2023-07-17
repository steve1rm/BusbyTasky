package me.androidbox.component.general

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.*

@Composable
fun UserInputTextField(
    modifier: Modifier = Modifier,
    isInputValid: Boolean,
    inputValue: String,
    placeholderText: String,
    onInputChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = inputValue,
        onValueChange = { newInput ->
            onInputChange(newInput)
        },
        placeholder = {
            Text(text = placeholderText, color = MaterialTheme.colorScheme.placeholderEntry)
        },
        trailingIcon = {
            if (isInputValid) {
                Image(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = "Valid input tick"
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isInputValid) {
                MaterialTheme.colorScheme.focusedInputEntryBorder
            } else {
                MaterialTheme.colorScheme.errorEmailEntry
            },
            unfocusedBorderColor = if (!isInputValid && inputValue.isNotBlank()) {
                MaterialTheme.colorScheme.errorEmailEntry
            } else {
                Color.Transparent
            },
            unfocusedTextColor = MaterialTheme.colorScheme.inputTextColor,
            focusedTextColor = MaterialTheme.colorScheme.inputTextColor
        ),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailEntryNotFocused() {
    BusbyTaskyTheme {
        UserInputTextField(
            isInputValid = false,
            inputValue = "steve@gmail.com",
            placeholderText = "name",
            onInputChange = { email ->
                Log.d("EMAIL_ENTRY", email)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailEntryFocused() {
    BusbyTaskyTheme {
        UserInputTextField(
            isInputValid = false,
            inputValue = "steve@gmail.com",
            placeholderText = "name",
            onInputChange = { email ->
                Log.d("EMAIL_ENTRY", email)
            }
        )
    }
}

@Composable
@Preview(showBackground = true, name = "Shows email entry with an valid email")
fun PreviewEmailEntryValidEmail() {
    BusbyTaskyTheme {
        val (text, setText) = remember { mutableStateOf("") }

        UserInputTextField(
            isInputValid = true,
            inputValue = text,
            placeholderText = "name",
            onInputChange = setText
        )
    }
}

@Composable
@Preview(showBackground = true, name = "Shows email entry with an invalid email")
fun PreviewEmailEntryInvalidEmail() {
    BusbyTaskyTheme {
        val (text, setText) = remember { mutableStateOf("") }

        UserInputTextField(
            isInputValid = true,
            inputValue = text,
            placeholderText = "name",
            onInputChange = setText
        )
    }
}
