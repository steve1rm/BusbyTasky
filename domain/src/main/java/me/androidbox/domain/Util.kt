package me.androidbox.domain

import java.util.Locale

fun String.toInitials(): String {
    val listOfName = this.trim().split(" ")
    var initials = ""

    listOfName.forEach { name ->
        initials += name.first()
    }

    return initials.uppercase(Locale.getDefault())
}