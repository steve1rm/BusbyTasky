package me.androidbox.component.agenda

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.OptionButton
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.EventCardBackgroundColor
import me.androidbox.component.ui.theme.ReminderCardBackgroundColor
import me.androidbox.component.ui.theme.TaskCardBackgroundColor

@Composable
fun AgendaCard(
    title: String,
    subtitle: String,
    dateTimeInfo: String,
    agendaCardType: AgendaCardType,
    modifier: Modifier = Modifier,
    isAgendaCompleted: Boolean = false,
    onMenuOptionClicked: () -> Unit,
    dropDownMenu: @Composable () -> Unit
) {
    Box(modifier = modifier
        .background(color = agendaCardType.backgroundColor())) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()) {
                Row(modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically) {
                    val agendaStatusIcon = if (isAgendaCompleted) {
                        painterResource(id = R.drawable.completed)
                    } else {
                        painterResource(id = R.drawable.incomplete)
                    }
                    Icon(painter = agendaStatusIcon, contentDescription = stringResource(R.string.agenda_status))
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = agendaCardType.titleTextColor,
                        textDecoration = if(isAgendaCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
                Box(
                    modifier = Modifier.align(Alignment.CenterEnd)) {
                    dropDownMenu()

                    OptionButton(
                        modifier = Modifier
                            .padding(top = 16.dp, end = 16.dp)
                            .align(Alignment.CenterEnd)
                            .clickable {
                                onMenuOptionClicked()
                            },
                        dotColor = agendaCardType.dotColor)
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                modifier = Modifier.padding(start = 48.dp),
                text = subtitle,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = agendaCardType.subTitleTextColor
            )
        }
        Text(
            modifier = Modifier
                .padding(bottom = 12.dp, end = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            text = dateTimeInfo,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = agendaCardType.subTitleTextColor
        )
    }
}

enum class AgendaCardType(
    val titleTextColor: Color,
    val subTitleTextColor: Color,
    val dotColor: Color,
    val backgroundColor: @Composable () -> Color,
) {
    TASK(
        titleTextColor = Color.White,
        subTitleTextColor = Color.White,
        dotColor = Color.White,
        backgroundColor = {
            MaterialTheme.colorScheme.TaskCardBackgroundColor
        }
    ),

    EVENT(
        titleTextColor = Color.Black,
        subTitleTextColor = Color.Black,
        dotColor = Color.Black,
        backgroundColor = {
            MaterialTheme.colorScheme.EventCardBackgroundColor
        },
    ),

    REMINDER(
        titleTextColor = Color.Black,
        subTitleTextColor = Color.Black,
        dotColor = Color.Black,
        backgroundColor = {
            MaterialTheme.colorScheme.ReminderCardBackgroundColor
        }
    )
}

@Composable
@Preview(showBackground = true, name = "Agenda card that is marked completed")
fun PreviewTaskCardIsCompleted() {
    BusbyTaskyTheme {
        AgendaCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(shape = RoundedCornerShape(22.dp)),
            agendaCardType = AgendaCardType.TASK,
            title = "Develop UI",
            subtitle = "Implement all agenda cards",
            dateTimeInfo = "Mar 5, 10:00",
            isAgendaCompleted = true,
            dropDownMenu = {},
            onMenuOptionClicked = {
                Log.d("AGENDA_ITEM", "Agenda Item has been tapped")
            })
    }
}

@Composable
@Preview(showBackground = true, name = "Agenda card that is marked not completed")
fun PreviewEventCardNotCompleted() {
    BusbyTaskyTheme {
        AgendaCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(shape = RoundedCornerShape(22.dp)),
            agendaCardType = AgendaCardType.EVENT,
            title = "Meeting",
            subtitle = "Adjust timeline and goals of project",
            dateTimeInfo = "Mar 5, 10:30 - Mar 5, 11:00",
            isAgendaCompleted = false,
            dropDownMenu = {},
            onMenuOptionClicked = {
                Log.d("AGENDA_ITEM", "Agenda Item has been tapped")
            })
    }
}

@Composable
@Preview(showBackground = true, name = "Agenda card that is marked completed")
fun PreviewReminderCardCompleted() {
    BusbyTaskyTheme {
        AgendaCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(shape = RoundedCornerShape(22.dp)),
            agendaCardType = AgendaCardType.REMINDER,
            title = "Lunch break",
            subtitle = "Continue to work during your lunch period",
            dateTimeInfo = "Mar 5, 13:00",
            isAgendaCompleted = true,
            dropDownMenu = {},
            onMenuOptionClicked = {
                Log.d("AGENDA_ITEM", "Agenda Item has been tapped")
            })
    }
}
