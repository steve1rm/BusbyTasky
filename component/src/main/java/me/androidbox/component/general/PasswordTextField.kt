package me.androidbox.component.general

import androidx.compose.foundation.clickable
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
fun PasswordTextField(
    modifier: Modifier = Modifier,
    passwordValue: String,
    placeholderText: String,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityClicked: () -> Unit,
    isPasswordVisible: Boolean
) {

    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = passwordValue,
        onValueChange = { newInput: String ->
            onPasswordChange(newInput)
        },
        placeholder = {
            Text(text = placeholderText, color = MaterialTheme.colorScheme.placeholderEntry)
        },
        trailingIcon = {
            val visibilityIconId = if(isPasswordVisible) {
                R.drawable.visible
            } else {
                R.drawable.hidden
            }
            IconButton(onClick = {
                onPasswordVisibilityClicked()
            }) {
                Icon(
                    modifier = Modifier.clickable {
                        onPasswordVisibilityClicked()
                    },
                    painter = painterResource(id = visibilityIconId),
                    contentDescription = "show hide the password"
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.inputTextColor,
            unfocusedTextColor = MaterialTheme.colorScheme.inputTextColor,
            focusedBorderColor = MaterialTheme.colorScheme.focusedInputEntryBorder,
            unfocusedBorderColor = Color.Transparent,
        ),
        visualTransformation = if (isPasswordVisible) {
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

        PasswordTextField(
            passwordValue = text,
            placeholderText = "Password",
            onPasswordChange = setText,
            onPasswordVisibilityClicked = {},
            isPasswordVisible = true
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

        PasswordTextField(
            passwordValue = text,
            placeholderText = "Password",
            onPasswordChange = setText,
            onPasswordVisibilityClicked = {},
            isPasswordVisible = false
        )
    }
}
