package me.androidbox.presentation.agenda.constant

import androidx.annotation.StringRes
import me.androidbox.component.R

enum class AgendaMenuActionType(@StringRes val titleId: Int) {
    OPEN(R.string.open),
    EDIT(R.string.edit),
    DELETE(R.string.delete)
}
