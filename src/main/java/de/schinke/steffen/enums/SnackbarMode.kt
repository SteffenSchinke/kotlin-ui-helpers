package de.schinke.steffen.enums

import de.schinke.steffen.ui.R

enum class SnackbarMode(val title: String, val iconId: Int) {

    ERROR("Fehler", R.drawable.ic_warning),
    TIP("Hinweis", R.drawable.ic_tip_outline),
    INFO("Info", R.drawable.ic_info_outline)
}