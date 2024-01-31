package app.extr.utils.helpers.json

import android.content.Context
import app.extr.data.types.Currency
import app.extr.data.types.ExpenseType
import app.extr.data.types.IncomeType
import app.extr.data.types.MoneyType
import app.extr.data.types.TransactionType
import app.extr.utils.helpers.CharConverter
import com.beust.klaxon.Klaxon

object JsonParsers {
    fun parseMoneyTypes(context: Context): List<MoneyType>? {
        val json = context.assets.open("MoneyTypes.json").bufferedReader().use { it.readText() }
        return Klaxon().parseArray<MoneyType>(json)
    }

    fun parseCurrencies(context: Context) : List<Currency>? {
        val json = context.assets.open("Currencies.json").bufferedReader().use { it.readText() }
        val klaxon = Klaxon().converter(CharConverter())
        return klaxon.parseArray<Currency>(json)
    }

    fun parseExpenseTypes(context: Context): List<ExpenseType>? {
        val json = context.assets.open("ExpenseTypes.json").bufferedReader().use { it.readText() }
        return Klaxon().parseArray<ExpenseType>(json)
    }

    fun parseIncomeTypes(context: Context): List<IncomeType>? {
        val json = context.assets.open("IncomeTypes.json").bufferedReader().use { it.readText() }
        return Klaxon().parseArray<IncomeType>(json)
    }
}