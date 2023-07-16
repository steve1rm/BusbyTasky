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
    contentType: ContentType,
    editScreenState: EditScreenState,
    editScreenEvent: (EditScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onSaveClicked: (content: String, contentType: ContentType) -> Unit
) {
    Scaffold(modifier = modifier,
        topBar = {
            AgendaItemEditTopAppBar(
                title = stringResource(id = contentType.contentTypeRes),
                modifier = Modifier.fillMaxWidth(),
                onSaveClicked = {
                    onSaveClicked(editScreenState.content, contentType)
                },
                onBackIconClicked = {
                    onBackClicked()
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.editScreenBackground)
        ) {

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
                textStyle = TextStyle(fontSize = contentType.fontSize, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.agendaBodyTextColor)
            )
        }
    }
}

enum class ContentType(@StringRes val contentTypeRes: Int, val fontSize: TextUnit) {
    TITLE(contentTypeRes = R.string.edit_title, fontSize = 26.sp),
    DESCRIPTION(contentTypeRes = R.string.edit_description, fontSize = 16.sp)
}

@Composable
@Preview(showBackground = true)
fun PreviewEditScreenTitle() {
    BusbyTaskyTheme {
        EditScreen(
            modifier = Modifier.fillMaxSize(),
            editScreenState = EditScreenState(content = "Meeting"),
            editScreenEvent = {},
            contentType = ContentType.TITLE,
            onBackClicked = {},
            onSaveClicked = { _, _ -> }
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
            contentType = ContentType.DESCRIPTION,
            onBackClicked = {},
            onSaveClicked = { _, _ -> }
        )
    }
}
