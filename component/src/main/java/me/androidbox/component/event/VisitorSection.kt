package me.androidbox.component.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.agenda.VisitorFilterType
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.visitorTextFontColor
import java.util.UUID
import kotlin.random.Random

@Composable
fun VisitorSection(
    visitors: List<VisitorInfo>,
    modifier: Modifier = Modifier,
    visitorDeleteClicked: (VisitorInfo) -> Unit,
    visitorFilterType: VisitorFilterType = VisitorFilterType.ALL,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            if (visitors.isNotEmpty()) {
                /** Going */
                if (visitorFilterType == VisitorFilterType.ALL || visitorFilterType == VisitorFilterType.GOING) {
                    item {
                        Text(
                            text = stringResource(id = R.string.going),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.visitorTextFontColor
                        )
                    }

                    items(visitors.filter { visitorInfo -> visitorInfo.isGoing }) { visitor ->
                        VisitorItem(
                            visitorInitials = "SM",
                            visitor = visitor,
                            isCreator = visitor.isCreator,
                            onDeleteClicked = visitorDeleteClicked
                        )
                    }
                }

                /* Not going */
                if (visitorFilterType == VisitorFilterType.ALL || visitorFilterType == VisitorFilterType.NOT_GOING) {
                    item {
                        Text(
                            text = stringResource(id = R.string.not_going),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.visitorTextFontColor
                        )
                    }

                    items(visitors.filter { visitorInfo -> !visitorInfo.isGoing }) { visitor ->
                        VisitorItem(
                            visitorInitials = visitor.initials,
                            visitor = visitor,
                            isCreator = visitor.isCreator,
                            onDeleteClicked = visitorDeleteClicked
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorSection() {
    BusbyTaskyTheme {
        VisitorSection(
            visitors = generateVisitorInfo(),
            visitorFilterType = VisitorFilterType.ALL,
            visitorDeleteClicked = {}
        )
    }
}

private fun generateVisitorInfo(): List<VisitorInfo> {
    return generateSequence {
        VisitorInfo(
            initials = "SM",
            fullName = UUID.randomUUID().toString(),
            userId = UUID.randomUUID().toString(),
            isGoing = Random.nextBoolean(),
            isCreator = Random.nextBoolean()
        )
    }.take(10).toList()
}