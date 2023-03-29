package me.androidbox.domain.agenda.usecase.imp

import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import java.util.*
import javax.inject.Inject

class UsersInitialsExtractionUseCaseImp @Inject constructor() : UsersInitialsExtractionUseCase {
    override fun execute(fullName: String): String {
        val listOfName = fullName.split(" ")
        var initials = ""

        listOfName.forEach { name ->
            initials += name.first()
        }

        return initials.uppercase(Locale.getDefault())
    }
}