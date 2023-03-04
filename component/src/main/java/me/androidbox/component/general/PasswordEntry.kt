package me.androidbox.component.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    onPasswordChange: (String) -> Unit,
) {

    val isPasswordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
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
            val visibilityIconId = if(isPasswordVisible.value) {
                R.drawable.visible
            } else {
                R.drawable.hidden
            }
            Icon(
                modifier = Modifier.clickable {
                    isPasswordVisible.value = !isPasswordVisible.value
                },
                painter = painterResource(id = visibilityIconId),
                contentDescription = "eye open close"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.focusedInputEntryBorder,
            unfocusedBorderColor = Color.Transparent,
            textColor = MaterialTheme.colorScheme.InputTextColor),
        visualTransformation = if (isPasswordVisible.value) {
            VisualTransformation.None
        }
        else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
@Preview(showBackground = true, name = "Shows password entry with viewable password")
fun PreviewPasswordEntryVisible() {
    BusbyTaskyTheme {
        val (text, setText) = remember {
            mutableStateOf("")
        }

        PasswordEntry(
            passwordValue = text,
            placeholderText = "Password",
            onPasswordChange = setText
        )
    }
}

@Composable
@Preview(showBackground = true, name = "Shows password entry with viewable password")
fun PreviewPasswordEntryHidden() {
    BusbyTaskyTheme {

        val (text, setText) = remember {
            mutableStateOf("")
        }

        PasswordEntry(
            passwordValue = text,
            placeholderText = "Password",
            onPasswordChange = setText
        )
    }
}
