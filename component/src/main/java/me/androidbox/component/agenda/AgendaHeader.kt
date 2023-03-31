package me.androidbox.component.agenda

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun AgendaHeader(
    agendaHeaderItem: AgendaHeaderItem,
    subTitle: String,
    description: String,
    isEditMode: Boolean,
    onEditTitleClicked: (title: String) -> Unit,
    onEditDescriptionClicked: (description: String) -> Unit,
    modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = agendaHeaderItem.drawableRes),
                contentDescription = stringResource(R.string.agenda_image))

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(agendaHeaderItem.titleRes),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaTitleHeaderColor
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(4F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
                Icon(painter = painterResource(id = R.drawable.incomplete), contentDescription = stringResource(R.string.subtitle_icon))

                /* TODO I had to just the padding as its aligned to the image and not the edge of the boarder */
                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = subTitle,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.agendaSubTitleHeaderColor
                )
            }
            
            Row(modifier = Modifier.weight(1F),
                horizontalArrangement = Arrangement.End
            ) {
                if(isEditMode) {
                    IconButton(onClick = {
                        onEditTitleClicked(subTitle)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.forward_arrow),
                            contentDescription = stringResource(R.string.forward_arrow),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(22.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.headerDividerColor)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = description,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.agendaSubTitleHeaderColor
            )

            Spacer(modifier = Modifier.width(14.dp))

            if(isEditMode) {
                IconButton(onClick = {
                    onEditDescriptionClicked(description)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.forward_arrow),
                        contentDescription = stringResource(R.string.forward_arrow),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, name = "Task agenda header")
fun PreviewAgendaHeaderEvent() {
    BusbyTaskyTheme {
        AgendaHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            agendaHeaderItem = AgendaHeaderItem.EVENT,
            subTitle = "Sample task",
            description = "Needs to complete the event details",
            onEditTitleClicked = {},
            isEditMode = true,
            onEditDescriptionClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true, name = "Event agenda header")
fun PreviewAgendaHeaderTask() {
    BusbyTaskyTheme {
        AgendaHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            agendaHeaderItem = AgendaHeaderItem.TASK,
            subTitle = "Sample event",
            description = "Needs to complete the task details",
            isEditMode = false,
            onEditTitleClicked = {},
            onEditDescriptionClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true, name = "Reminder agenda header")
fun PreviewAgendaHeaderReminder() {
    BusbyTaskyTheme {
        AgendaHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            agendaHeaderItem = AgendaHeaderItem.REMINDER,
            subTitle = "Sample reminder",
            description = "Needs to complete",
            isEditMode = true,
            onEditTitleClicked = {},
            onEditDescriptionClicked = {}
        )
    }
}

enum class AgendaHeaderItem(@StringRes val titleRes: Int, @DrawableRes val drawableRes: Int) {
    EVENT(titleRes = R.string.event, drawableRes = R.drawable.event),
    TASK(titleRes = R.string.task, drawableRes = R.drawable.task),
    REMINDER(titleRes = R.string.reminder, drawableRes = R.drawable.reminder)
}