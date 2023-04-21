package me.androidbox.presentation.mapper

import me.androidbox.component.event.VisitorInfo
import me.androidbox.domain.agenda.model.Attendee

fun Attendee.toVisitorInfo(isCreator: Boolean): VisitorInfo {
    return VisitorInfo(
        fullName = this.fullName,
        userId = this.userId,
        isGoing = this.isGoing,
        isCreator = isCreator
    )
}
