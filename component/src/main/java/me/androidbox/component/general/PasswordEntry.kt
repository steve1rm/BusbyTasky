package me.androidbox.component.general

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEntry(
    modifier: Modifier = Modifier,
    passwordValue: String,
    placeholderText: String,
    visibilityTapped: () -> Boolean,
    onPasswordChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().background(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.backgroundInputEntry
        ),
        singleLine = true,
        value = passwordValue,
        onValueChange = { newInput: String ->
            onPasswordChange(newInput)
        },
        placeholder = {
            Text(text = placeholderText, color = MaterialTheme.colorScheme.placeholderEntry)
        },
        trailingIcon = {
            val visibilityIconId = if(visibilityTapped()) {
                R.drawable.hidden
            } else {
                R.drawable.visible
            }
            Image(
                painter = painterResource(id = visibilityIconId),
                contentDescription = "eye open close"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.focusedInputEntryBorder,
            unfocusedBorderColor = Color.Transparent),
        visualTransformation = if (visibilityTapped()) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}

@Composable
@Preview(showBackground = true, name = "Shows password entry with viewable password")
fun PreviewPasswordEntryVisible() {
    BusbyTaskyTheme {
        PasswordEntry(
            visibilityTapped = { false },
            passwordValue = "1234567890",
            placeholderText = "Password",
            onPasswordChange = { email ->
                Log.d("PASSWORD_ENTRY", email)
            }
        )
    }
}

@Composable
@Preview(showBackground = true, name = "Shows password entry with viewable password")
fun PreviewPasswordEntryHidden() {
    BusbyTaskyTheme {
        PasswordEntry(
            visibilityTapped = { true },
            passwordValue = "1234567890",
            placeholderText = "Password",
            onPasswordChange = { email ->
                Log.d("PASSWORD_ENTRY", email)
            }
        )
    }
}
