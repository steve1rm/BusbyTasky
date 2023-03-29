package me.androidbox.presentation.edit.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.AgendaItemEditTopAppBar
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.divider
import me.androidbox.component.ui.theme.editScreenBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    titleType: TitleType,
    editScreenState: EditScreenState,
    editScreenEvent: (EditScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    onSaveClicked: () -> Unit,
    onTopIconClicked: () -> Unit,
) {
    Scaffold(modifier = modifier,
        topBar = {
            val contentType = when(titleType) {
                TitleType.TITLE -> stringResource(id = TitleType.TITLE.contentTypeRes)
                TitleType.DESCRIPTION -> stringResource(id = TitleType.DESCRIPTION.contentTypeRes)
            }

            AgendaItemEditTopAppBar(
                title = contentType,
                modifier = Modifier.fillMaxWidth(),
                onSaveClicked = onSaveClicked,
                onBackIconClicked = onTopIconClicked
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.editScreenBackground)
        ) {

            val fontSize =  when(titleType) {
                TitleType.TITLE -> TitleType.TITLE.fontSize
                TitleType.DESCRIPTION -> TitleType.DESCRIPTION.fontSize
            }

            Divider(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.divider,
                thickness = 2.dp)

            TextField(
                modifier = Modifier
                    .fillMaxSize(),
                singleLine = false,
                value = editScreenState.content,
                onValueChange = { newContent ->
                    editScreenEvent(EditScreenEvent.OnContentChanged(newContent))
                },
                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.editScreenBackground),
                textStyle = TextStyle(fontSize = fontSize, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.agendaBodyTextColor)
            )
        }
    }
}

enum class TitleType(@StringRes val contentTypeRes: Int, val fontSize: TextUnit) {
    TITLE(contentTypeRes = R.string.edit_title, fontSize =  26.sp),
    DESCRIPTION(contentTypeRes = R.string.edit_description, fontSize =  16.sp)
}

@Composable
@Preview(showBackground = true)
fun PreviewEditScreenTitle() {
    BusbyTaskyTheme {
        EditScreen(
            modifier = Modifier.fillMaxSize(),
            editScreenState = EditScreenState(content = "Meeting"),
            editScreenEvent = {},
            titleType = TitleType.TITLE,
            onTopIconClicked = {},
            onSaveClicked = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditScreenDescription() {
    BusbyTaskyTheme {
        EditScreen(
            modifier = Modifier.fillMaxSize(),
            editScreenState = EditScreenState(content = "This is the description of the project"),
            editScreenEvent = {},
            titleType = TitleType.DESCRIPTION,
            onTopIconClicked = {},
            onSaveClicked = {},
        )
    }
}
