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
    content: MutableState<String>,
    titleType: TitleType
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            val contentType = when(titleType) {
                TitleType.TITLE -> stringResource(id = TitleType.TITLE.contentTypeRes)
                TitleType.DESCRIPTION -> stringResource(id = TitleType.DESCRIPTION.contentTypeRes)
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
                content.value = newContent
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
            content = editableContent,
            titleType = TitleType.TITLE)
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
            content = editableContent,
            titleType = TitleType.DESCRIPTION
        )
    }
}

enum class TitleType(@StringRes val contentTypeRes: Int) {
    TITLE(R.string.edit_title),
    DESCRIPTION(R.string.edit_description)
}
