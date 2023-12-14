package app.extr.utils.helpers.resproviders

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

abstract class CategoryResAbstract {
    data class CategoryAttributes(@DrawableRes val icon: Int, @ColorRes val color: Color)

    fun getAttributesById(id: Int): CategoryAttributes {
        //return getPredefinedAttributes(id) ?: getCustomAttributes(id) ?: defaultAttributes()
        return getPredefinedAttributes(id) ?: defaultAttributes()
    }

    protected abstract fun getPredefinedAttributes(id: Int): CategoryAttributes?
    //protected abstract fun getCustomAttributes(id: Int): CategoryAttributes?
    protected abstract fun defaultAttributes() : CategoryAttributes
}