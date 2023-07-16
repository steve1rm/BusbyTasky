package me.androidbox.component.general

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.buttonColor
import me.androidbox.component.ui.theme.loginTextColor

@Composable
fun TaskButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    buttonTextColor: Color = MaterialTheme.colorScheme.loginTextColor,
    backgroundColor: Color = MaterialTheme.colorScheme.buttonColor,
    onButtonClick: () -> Unit,
    borderRadius: Dp = 38.dp,
    isLoading: Boolean = false,
    loadingColor: Color = Color.White
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(borderRadius),
        onClick = {
            onButtonClick()
        }) {

        if (isLoading) {
            CircularProgressIndicator(color = loadingColor)
        } else {
            Text(
                text = buttonText,
                color = buttonTextColor,
                fontSize = textSize,
                fontWeight = fontWeight
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTaskButton() {
    BusbyTaskyTheme {
        TaskButton(
            modifier = Modifier,
            buttonText = "Login",
            onButtonClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTaskButtonIsLoading() {
    BusbyTaskyTheme {
        TaskButton(
            modifier = Modifier,
            buttonText = "Login",
            onButtonClick = {},
            isLoading = true
        )
    }
}