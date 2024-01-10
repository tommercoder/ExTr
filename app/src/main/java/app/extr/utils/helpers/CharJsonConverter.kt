package app.extr.utils.helpers

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue

class CharConverter : Converter {
    override fun canConvert(cls: Class<*>) = cls == Char::class.java

    override fun fromJson(jv: JsonValue) = jv.string?.first() ?: ' ' // Default to ' ' if null or empty

    override fun toJson(value: Any) = """ "${value as Char}" """
}