package me.androidbox.presentation.task.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@Composable
fun TaskDetailScreen(
    taskDetailScreenState: TaskDetailScreenState,
    taskDetailScreenEvent: (taskDetailScreenEvent: TaskDetailScreenEvent) -> Unit
    onEditTitleClicked: (title: String) -> Unit,
    onEditDescriptionClicked: (description: String) -> Unit,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

}


@Composable
@Preview(showBackground = true)
fun PreviewTaskDetailScreen() {
    BusbyTaskyTheme {
        TaskDetailScreen(
            taskDetailScreenState = TaskDetailScreenState(),
            taskDetailScreenEvent = {},
            onEditTitleClicked = {},
            onEditDescriptionClicked = {},
            onCloseClicked = {}
        )
    }
}