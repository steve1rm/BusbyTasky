package me.androidbox.domain.agenda.model

data class Agenda(
    val events: List<Event>,
    val tasks: List<Task>,
    val reminders: List<Reminder>
)
