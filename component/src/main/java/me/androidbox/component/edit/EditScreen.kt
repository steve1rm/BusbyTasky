package me.androidbox.component.edit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.component.R
import me.androidbox.component.general.AgendaTopAppBar
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    editableContent: MutableState<String>,
    editContentType: EditContentType
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            val contentType = when(editContentType) {
                EditContentType.TITLE -> stringResource(id = EditContentType.TITLE.contentTypeRes)
                EditContentType.DESCRIPTION -> stringResource(id = EditContentType.DESCRIPTION.contentTypeRes)
            }

            AgendaTopAppBar(
                title = contentType,
                onSaveClicked = {},
                onTopIconClicked = {}
            )
        }
    ) {
        TextField(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            singleLine = false,
            value = "Edit your project",
            onValueChange = { newContent ->
                editableContent.value = newContent
            })
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditScreenTitle() {
    BusbyTaskyTheme {
        val editableContent = remember {
            mutableStateOf("")
        }

        EditScreen(
            editableContent = editableContent,
            editContentType = EditContentType.TITLE)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditScreenDescription() {
    BusbyTaskyTheme {
        val editableContent = remember {
            mutableStateOf("")
        }

        EditScreen(
            editableContent = editableContent,
            editContentType = EditContentType.DESCRIPTION
        )
    }
}

enum class EditContentType(@StringRes val contentTypeRes: Int) {
    TITLE(R.string.edit_title),
    DESCRIPTION(R.string.edit_description)
}
