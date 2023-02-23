package me.androidbox.component.general

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputEntry(
    modifier: Modifier = Modifier,
    isInputValid: Boolean,
    inputValue: String,
    placeholderText: String,
    onInputChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.backgroundInputEntry
            ),
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if(isInputValid) {
                MaterialTheme.colorScheme.focusedInputEntryBorder
            }
            else {
                MaterialTheme.colorScheme.errorEmailEntry
            },
            unfocusedBorderColor = Color.Transparent,
            textColor = MaterialTheme.colorScheme.InputTextColor),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailEntryNotFocused() {
    BusbyTaskyTheme {
        UserInputEntry(
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
        UserInputEntry(
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

        UserInputEntry(
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

        UserInputEntry(
            isInputValid = true,
            inputValue = text,
            placeholderText = "name",
            onInputChange = setText
        )
    }
}
