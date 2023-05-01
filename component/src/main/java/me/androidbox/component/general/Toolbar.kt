package me.androidbox.component.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.White

@Composable
fun Toolbar(
    title: String,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )

        IconButton(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd),
            onClick = {
                onCloseClicked()
            }
        ) {
            Icon(
                painterResource(id = R.drawable.close),
                contentDescription = "Close the bottom sheet",
                tint = Color.Black,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewToolbar() {
    BusbyTaskyTheme {
        Toolbar(
            title = "Alert Reminder",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White)
                .padding(vertical = 16.dp, horizontal = 16.dp),
            onCloseClicked = {})
    }
}