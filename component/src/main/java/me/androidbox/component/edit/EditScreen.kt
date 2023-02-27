package me.androidbox.component.edit

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.AgendaTopAppBar
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.divider
import me.androidbox.component.ui.theme.editScreenBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    content: MutableState<String>,
    titleType: TitleType,
    onSaveClicked: () -> Unit,
    onTopIconClicked: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            val contentType = when(titleType) {
                TitleType.TITLE -> stringResource(id = TitleType.TITLE.contentTypeRes)
                TitleType.DESCRIPTION -> stringResource(id = TitleType.DESCRIPTION.contentTypeRes)
            }

            AgendaTopAppBar(
                title = contentType,
                onSaveClicked = onSaveClicked,
                onTopIconClicked = onTopIconClicked
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.editScreenBackground)
        ) {

            val fontSize =  when(titleType) {
                TitleType.TITLE -> 26.sp
                TitleType.DESCRIPTION -> 16.sp
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
                value = content.value,
                onValueChange = { newContent ->
                    content.value = newContent
                },
                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.editScreenBackground),
                textStyle = TextStyle(fontSize = fontSize, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.agendaBodyTextColor)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewEditScreenTitle() {
    BusbyTaskyTheme {
        val editableContent = remember {
            mutableStateOf("Meeting")
        }

        EditScreen(
            content = editableContent,
            titleType = TitleType.TITLE,
            onTopIconClicked = {},
            onSaveClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditScreenDescription() {
    BusbyTaskyTheme {
        val editableContent = remember {
            mutableStateOf("This is the description of the project")
        }

        EditScreen(
            content = editableContent,
            titleType = TitleType.DESCRIPTION,
            onTopIconClicked = {},
            onSaveClicked = {}
        )
    }
}

enum class TitleType(@StringRes val contentTypeRes: Int, fontSize: TextUnit) {
    TITLE(R.string.edit_title, 26.sp),
    DESCRIPTION(R.string.edit_description, 16.sp)
}
