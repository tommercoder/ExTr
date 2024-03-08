package app.extr.utils.helpers.resproviders

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import app.extr.R
import app.extr.utils.helpers.Constants
import app.extr.ui.theme.md_theme_light_primary

data class ResIconColor(
    @DrawableRes val icon: Int,
    @ColorRes val color: Color)

abstract class ResProvider {
    protected abstract val predefinedAttributes: Map<Int, ResIconColor>

    @DrawableRes
    private val defaultIcon = R.drawable.question_mark
    @ColorRes
    private val defaultColor = Color.Unspecified

    fun getRes(id: Int): ResIconColor {
        return predefinedAttributes[id] ?: ResIconColor(defaultIcon, defaultColor)
    }

//    todo: remove if not used

//    fun findIconIdByRes(icon: Int): Int {
//        return predefinedAttributes.entries.firstOrNull { it.value.icon == icon }?.key
//            ?: Constants.DefaultIconId
//    }
//
//    fun findColorIdByRes(color: Color): Int {
//        return predefinedAttributes.entries.firstOrNull { it.value.color == color }?.key
//            ?: Constants.DefaultColorId
//    }
}