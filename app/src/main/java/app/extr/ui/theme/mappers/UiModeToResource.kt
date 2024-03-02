package app.extr.ui.theme.mappers

import app.extr.R
import app.extr.data.types.UiMode

fun UiMode.toResourceString(): Int {
    return when (this) {
        UiMode.AUTO -> R.string.ui_mode_auto
        UiMode.LIGHT -> R.string.ui_mode_light
        UiMode.DARK -> R.string.ui_mode_dark
    }
}