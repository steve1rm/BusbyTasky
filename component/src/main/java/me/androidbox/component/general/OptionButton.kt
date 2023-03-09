package me.androidbox.component.general

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.DarkOptionButton

@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    dotColor: Color = MaterialTheme.colorScheme.DarkOptionButton) {
    Row(modifier.wrapContentWidth()) {
        Icon(
            painter = painterResource(id = R.drawable.white_dot),
            contentDescription = "option button 1",
            tint = dotColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Icon(
            painter = painterResource(id = R.drawable.white_dot),
            contentDescription = "option button 2",
            tint = dotColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Icon(
            painter = painterResource(id = R.drawable.white_dot),
            contentDescription = "option button 3",
            tint = dotColor
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewOptionButton() {
    BusbyTaskyTheme {
        OptionButton()
    }
}