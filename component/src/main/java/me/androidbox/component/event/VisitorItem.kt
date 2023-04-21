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
import me.androidbox.component.R
import me.androidbox.component.ui.theme.*
import java.util.UUID

@Composable
fun VisitorItem(
    visitorInitials: String,
    visitor: VisitorInfo,
    isCreator: Boolean = true,
    modifier: Modifier = Modifier,
    onDeleteClicked: (visitor: VisitorInfo) -> Unit) {

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
            text = visitorInitials,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.visitorInitialsFontColor,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .weight(1F)
                .padding(start = 16.dp),
            text = visitor.fullName,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.visitorTextFontColor,
            fontWeight = FontWeight.Normal)

        if (isCreator) {
            Text(
                modifier = modifier.padding(end = 16.dp),
                text = stringResource(R.string.creator),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.creatorTextFontColor,
                fontWeight = FontWeight.Normal)

        }
        else {
            IconButton(onClick = {
                onDeleteClicked(visitor)
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
        VisitorItem(
            visitorInitials = "SM",
            visitor = VisitorInfo(
                initials = "SM",
                "Steve Mason",
                UUID.randomUUID().toString(),
                isGoing = true,
                isCreator = true
            ),
            isCreator = true
        ) {}
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorIsVisitor() {
    BusbyTaskyTheme {
        VisitorItem(
            visitorInitials = "SM",
            visitor = VisitorInfo(
                initials = "SM",
                "Steve Mason",
                UUID.randomUUID().toString(),
                isGoing = true,
                isCreator = true
            ),
            isCreator = false
        ) {}
    }
}