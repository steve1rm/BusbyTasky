package me.androidbox.data.model.response

data class FullAgendaModel(
    val listOfEventId: List<String>,
    val listOfTaskId: List<String>,
    val listOfReminderId: List<String>
)
