package app.extr.utils.helpers.resproviders

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import app.extr.R
import app.extr.ui.theme.Purple80

data class ResIconColor(@DrawableRes val icon: Int, @ColorRes val color: Color)

abstract class ResProvider {
    protected abstract val predefinedAttributes: Map<Int, ResIconColor>

    @DrawableRes private val defaultIcon = R.drawable.ic_launcher_background
    @ColorRes private val defaultColor = Purple80

    fun getRes(id: Int): ResIconColor {
        return predefinedAttributes[id] ?: ResIconColor(defaultIcon, defaultColor)
    }
}