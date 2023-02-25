package me.androidbox.component.general

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import me.androidbox.component.ui.theme.loginTextColor
import me.androidbox.component.ui.theme.buttonColor

@Composable
fun TaskButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    textSize: TextUnit = 16.sp,
    buttonTextColor: Color = MaterialTheme.colorScheme.loginTextColor,
    backgroundColor: Color = MaterialTheme.colorScheme.buttonColor,
    onButtonClick: () -> Unit,
    radius: Dp= 38.dp,
    height: Dp = 50.dp
) {
    Button(
        modifier = modifier
            .height(height)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(radius),
        onClick = {
            onButtonClick()
        }) {
        Text(
            text = buttonText,
            color = buttonTextColor,
            fontSize = textSize,
            fontWeight = FontWeight.Bold
        )
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