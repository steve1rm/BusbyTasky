package me.androidbox.component.general

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.backgroundInputEntry
import me.androidbox.component.ui.theme.placeholderEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailEntry(
    modifier: Modifier = Modifier,
    isValidEmail: Boolean,
    onEmailChange: (String) -> Unit
) {
    var emailValue by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier.background(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.backgroundInputEntry
        ),
        singleLine = true,
        value = emailValue,
        onValueChange = { newInput ->
            emailValue = newInput
            onEmailChange(newInput)
        },
        placeholder = {
            Text(text = "Email address", color = MaterialTheme.colorScheme.placeholderEntry)
        },
        trailingIcon = {
            if (isValidEmail) {
                Image(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = "Valid email tick"
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailEntryNotFocused() {
    Surface {
        EmailEntry(
            isValidEmail = false,
            onEmailChange = { email ->
                Log.d("EMAIL_ENTRY", email)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailEntryFocused() {
    Surface {
        EmailEntry(
            isValidEmail = false,
            onEmailChange = { email ->
                Log.d("EMAIL_ENTRY", email)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEmailEntryValidEmail() {
    Surface {
        EmailEntry(
            isValidEmail = true,
            onEmailChange = { email ->
                Log.d("EMAIL_ENTRY", email)
            }
        )
    }
}
