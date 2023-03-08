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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.OptionButton
import me.androidbox.component.ui.theme.*

@Composable
fun AgendaCard(
    modifier: Modifier = Modifier,
    titleTextColor: Color = Color.Black,
    subTitleTextColor: Color = MaterialTheme.colorScheme.AgendaBodyTextColor,
    backgroundColor: Color = MaterialTheme.colorScheme.EventCardBackgroundColor,
    dotColor: Color = Color.White,
    isAgendaCompleted: Boolean = false,
    onMenuOptionClicked: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp).background(color = backgroundColor, shape = RoundedCornerShape(22.dp))) {
        Column {
            Box(modifier = modifier
                .fillMaxWidth()) {
                Row(modifier = Modifier.padding(start = 16.dp, top = 16.dp).align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically) {
                    val agendaStatusIcon = if (isAgendaCompleted) {
                        painterResource(id = R.drawable.completed)
                    } else {
                        painterResource(id = R.drawable.incomplete)
                    }
                    Icon(painter = agendaStatusIcon, contentDescription = "Agenda status")
                    Spacer(modifier = Modifier.width(16.dp))

                    if(isAgendaCompleted) {
                        Text(text = "Meeting", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = titleTextColor ,textDecoration = TextDecoration.LineThrough)
                    }
                    else {
                        Text(text = "Meeting", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = titleTextColor)
                    }
                }
                OptionButton(
                    modifier = Modifier.padding(top = 16.dp, end = 16.dp).align(Alignment.CenterEnd).clickable {
                        onMenuOptionClicked()
                    },
                    dotColor = dotColor)
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(modifier = Modifier.padding(start = 48.dp),
                text = "This is the project tasky that needs", fontWeight = FontWeight.Normal, fontSize = 14.sp, color = subTitleTextColor)
        }
        Text(modifier = Modifier.padding(bottom = 12.dp, end = 16.dp).align(alignment = Alignment.BottomEnd) ,
            text = "Mar 5, 10:30 - Mar 5, 11:00", fontWeight = FontWeight.Normal, fontSize = 14.sp, color = subTitleTextColor)
    }
}

@Composable
@Preview(showBackground = true, name = "Agenda card that is marked completed")
fun PreviewAgendaCardIsCompleted() {
    BusbyTaskyTheme {
        AgendaCard(
            titleTextColor = Color.White,
            backgroundColor = MaterialTheme.colorScheme.TaskCardBackgroundColor,
            subTitleTextColor = Color.White,
            isAgendaCompleted = true)  {
            Log.d("AGENDA_ITEM", "Agenda Item has been tapped")
        }
    }
}

@Composable
@Preview(showBackground = true, name = "Agenda card that is marked not completed")
fun PreviewAgendaCardNotCompleted() {
    BusbyTaskyTheme {
        AgendaCard(
            dotColor = Color.White,
            backgroundColor = MaterialTheme.colorScheme.EventCardBackgroundColor,
            titleTextColor = Color.White,
            subTitleTextColor = Color.White,
            isAgendaCompleted = false)  {
            Log.d("AGENDA_ITEM", "Agenda Item has been tapped")
        }
    }
}


@Composable
@Preview(showBackground = true, name = "Agenda card that is marked completed")
fun PreviewAgendaCardCompletedGreen() {
    BusbyTaskyTheme {
        AgendaCard(
            dotColor = MaterialTheme.colorScheme.DarkOptionButton,
            backgroundColor = MaterialTheme.colorScheme.EventCardBackgroundColor,
            titleTextColor = Color.Black,
            subTitleTextColor = MaterialTheme.colorScheme.AgendaBodyTextColor,
            isAgendaCompleted = true) {
            Log.d("AGENDA_ITEM", "Agenda Item has been tapped")
        }
    }
}
