package me.androidbox.component.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.*

@Composable
fun Visitor(
    modifier: Modifier = Modifier,
    visitorName: String,
    isCreator: Boolean = true,
    onDeleteClicked: () -> Unit) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.visitorBackgroundColor),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.LightGray,
                        radius = this.size.maxDimension
                    )
                },
            text = "SM",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.visitorInitialsFontColor,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .weight(1F)
                .padding(start = 16.dp),
            text = visitorName,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.visitorTextFontColor,
            fontWeight = FontWeight.Normal)

        if (isCreator) {
            Text(
                text = stringResource(R.string.creator),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.creatorTextFontColor,
                fontWeight = FontWeight.Normal)

        }
        else {
            IconButton(onClick = {
                onDeleteClicked()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.bin),
                    contentDescription = "delete from agenda"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorIsCreator() {
    BusbyTaskyTheme {
        Visitor(
            onDeleteClicked = {},
            visitorName = "Steve Mason",
            isCreator = true
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewVisitorIsVistor() {
    BusbyTaskyTheme {
        Visitor(
            onDeleteClicked = {},
            visitorName = "Steve Mason",
            isCreator = false
        )
    }
}