package app.extr.utils.helpers.json

import android.content.Context
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType
import com.beust.klaxon.Klaxon

object JsonParsers {
    fun parseMoneyTypes(context: Context): List<MoneyType>? {
        val json = context.assets.open("MoneyTypes.json").bufferedReader().use { it.readText() }
        return Klaxon().parseArray<MoneyType>(json)
    }

    fun parseCurrencies(context: Context) : List<Currency>? {
        val json = context.assets.open("Currencies.json").bufferedReader().use { it.readText() }
        return Klaxon().parseArray<Currency>(json)
    }
}