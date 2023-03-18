package me.androidbox.component.general

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@Composable
fun TaskActionButton(
    @DrawableRes iconResource: Int,
    onActionClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onActionClicked()
        }) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = "Arrow back",
            tint = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewActionButton() {
    BusbyTaskyTheme {
        TaskActionButton(
            modifier =  Modifier
                .size(56.dp)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(size = 16.dp)
                ),
            iconResource = R.drawable.add_white,
            onActionClicked = {}
        )
    }
}