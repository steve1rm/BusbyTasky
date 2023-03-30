package me.androidbox.presentation.event.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.agenda.*
import me.androidbox.component.general.AgendaItemEditTopAppBar
import me.androidbox.component.general.PhotoPicker
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundWhiteColor
import me.androidbox.presentation.edit.screen.EditScreenEvent
import me.androidbox.presentation.edit.screen.TitleType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier,
        topBar = {
            AgendaItemEditTopAppBar(
                title = stringResource(id = TitleType.TITLE.contentTypeRes),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black),
                onSaveClicked = {
           //         editScreenEvent(EditScreenEvent.OnSaveClicked)
                },
                onBackIconClicked = {}
            )

        },
        content = {
            Column(modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .background(Color.Black)) {

                Column (modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = modifier.height(30.dp))
                    AgendaHeader(agendaHeaderItem = AgendaHeaderItem.EVENT, subTitle = "Meeting")

                    PhotoPicker(listOfPhotoUri = emptyList(), onPhotoUriSelected = {

                    })

                    Spacer(modifier = modifier.height(26.dp))
                    AgendaDuration(
                        startTime = "08:00",
                        endTime = "08:30",
                        startDate = "Jul 21 2022",
                        endDate = "Jul 21 2022",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = modifier.height(26.dp))
                    AlarmReminder(
                        reminderText = "30 minutes before",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.backgroundWhiteColor)
                    )
                    Spacer(modifier = modifier.height(26.dp))

                    VisitorFilter(
                        modifier = Modifier.fillMaxWidth(),
                        selectedVisitorType = VisitorType.ALL,
                        onSelectedTypeClicked = {}
                    )


                    Spacer(modifier = modifier.height(26.dp))

                    AgendaAction(
                        agendaActionType = AgendaActionType.LEAVE_EVENT,
                        onActionClicked = {}
                    )
/*
            VisitorSection(titleResId = R.string.going) {
                Visitor(visitorInitials = "SM", visitorName = "Steve Mason") {
                }
            }
*/

       //             Spacer(modifier = modifier.height(26.dp))
/*
            VisitorSection(titleResId = R.string.not_going) {
                Visitor(visitorInitials = "PR", visitorName = "Peter Rabbit", isCreator = false) {
                }
            }
*/

      //              Spacer(modifier = modifier.height(16.dp))
/*
            AgendaAction(headingResId = R.string.delete_reminder, showTopDivider = true) {
            }
*/
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewEventScreen() {
    BusbyTaskyTheme {
        EventScreen(
            modifier = Modifier.fillMaxWidth()
        )
    }
}