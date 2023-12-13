package app.extr.ui.theme.helpers

import android.content.Context
import androidx.annotation.DrawableRes

interface ResourceProvider {
    @DrawableRes
    fun getIconByname(name: String): Int
}

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getIconByname(name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}