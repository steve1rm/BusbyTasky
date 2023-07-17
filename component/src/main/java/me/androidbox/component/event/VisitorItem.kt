package me.androidbox.component.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import java.util.UUID
import me.androidbox.component.R
import me.androidbox.component.ui.theme.Black
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.creatorTextFontColor
import me.androidbox.component.ui.theme.visitorBackgroundColor
import me.androidbox.component.ui.theme.visitorInitialsFontColor
import me.androidbox.component.ui.theme.visitorTextFontColor

@Composable
fun VisitorItem(
    initials: String,
    fullName: String,
    userId: String,
    isCreator: Boolean = true,
    modifier: Modifier = Modifier,
    onDeleteClicked: (userId: String) -> Unit) {

    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.visitorBackgroundColor, shape = RoundedCornerShape(10.dp)),
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
            text = initials,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.visitorInitialsFontColor,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(start = 16.dp),
            text = fullName,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.visitorTextFontColor,
            fontWeight = FontWeight.Normal)

        if (isCreator) {
            Text(
                modifier = modifier.padding(end = 4.dp),
                text = stringResource(R.string.creator),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.creatorTextFontColor,
                fontWeight = FontWeight.Normal)
        } else {
            IconButton(onClick = {
                onDeleteClicked(userId)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.bin),
                    contentDescription = "delete from agenda",
                    tint = Black
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorIsCreator() {
    BusbyTaskyTheme {
        VisitorItem(
            initials = "SM",
            fullName = "Bee",
            userId = UUID.randomUUID().toString(),
            isCreator = true,
            onDeleteClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorIsVisitor() {
    BusbyTaskyTheme {
        VisitorItem(
            initials = "SM",
            fullName = "Steve Mason",
            userId = UUID.randomUUID().toString(),
            isCreator = false,
            onDeleteClicked = {}
        )
    }
}