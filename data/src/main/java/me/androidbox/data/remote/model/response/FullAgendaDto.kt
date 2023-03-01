package me.androidbox.data.remote.model.response

data class FullAgendaDto(
    val listOfEventId: List<String>,
    val listOfTaskId: List<String>,
    val listOfReminderId: List<String>
)
