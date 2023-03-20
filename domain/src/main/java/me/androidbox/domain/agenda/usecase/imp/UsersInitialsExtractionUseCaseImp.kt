package me.androidbox.domain.agenda.usecase.imp

import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import java.util.*
import javax.inject.Inject

class UsersInitialsExtractionUseCaseImp @Inject constructor() : UsersInitialsExtractionUseCase {
    override fun execute(fullName: String): String {
        val listOfFullName = fullName.split(" ").take(2)

        /** Only take 2 names i.e. first name and last name */
        return "${listOfFullName.first()} ${listOfFullName.last()}".uppercase(Locale.getDefault())
    }
}