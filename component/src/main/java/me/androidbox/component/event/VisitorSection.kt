package me.androidbox.component.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        if (visitors.isNotEmpty()) {
            /** Going */
            if (visitorFilterType == VisitorFilterType.ALL || visitorFilterType == VisitorFilterType.GOING) {
                Text(
                    text = stringResource(id = R.string.going),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.visitorTextFontColor
                )
                Spacer(modifier = Modifier.height(16.dp))

                visitors.filter { visitorInfo -> visitorInfo.isGoing }.forEach { visitor ->
                    Spacer(modifier = Modifier.height(6.dp))

                    VisitorItem(
                        visitorInitials = "SM",
                        visitor = visitor,
                        isCreator = visitor.isCreator,
                        onDeleteClicked = { visitorInfo ->
                            visitorDeleteClicked(visitorInfo)
                        }
                    )
                }
            }

            /* Not going */
            if (visitorFilterType == VisitorFilterType.ALL || visitorFilterType == VisitorFilterType.NOT_GOING) {
                Text(
                    text = stringResource(id = R.string.not_going),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.visitorTextFontColor
                )
                Spacer(modifier = Modifier.height(16.dp))

                visitors.filter { visitorInfo -> !visitorInfo.isGoing }.forEach { visitor ->
                    Spacer(modifier = Modifier.height(6.dp))

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
    }.take(20).toList()
}