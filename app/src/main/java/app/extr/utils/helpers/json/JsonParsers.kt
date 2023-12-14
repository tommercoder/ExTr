package app.extr.utils.helpers.json

import android.content.Context
import app.extr.data.types.MoneyType
import com.beust.klaxon.Klaxon

object JsonParsers {
    fun parseMoneyTypes(context: Context): List<MoneyType>? {
        val json = context.assets.open("MoneyTypes.json").bufferedReader().use { it.readText() }
        return Klaxon().parseArray<MoneyType>(json)
    }
}