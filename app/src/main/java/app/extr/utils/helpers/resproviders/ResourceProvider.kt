package app.extr.utils.helpers

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

//TODO: delete if not used afterall
interface ResourceProvider {
    @DrawableRes
    fun getIconByname(name: String): Int
}

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getIconByname(name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}

object Res {
//    inline fun <reified T> getIconById(id: Int) where T : Enum<T>, T : DatabaseMapperEnum =
//        enumValues<T>()
//            .firstOrNull { it.storedId == id }?.iconId
//    inline fun <reified T> getColorById(id: Int) where T : Enum<T>, T : DatabaseMapperEnum =
//        enumValues<T>()
//            .firstOrNull { it.storedId == id }?.colorId
}

