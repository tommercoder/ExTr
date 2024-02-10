package app.extr.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

//balances
val balance_light_card_color = Color(0xFFF7E0D5)
val balance_light_cash_color = Color(0XFFE2EBFD)
val balance_light_savings_color = Color(0XFFFBF2C0)
val balance_light_electronic_wallet_color = Color(0XFFC4FFDF)

val balance_dark_card_color = Color(0XFF7B706A)
val balance_dark_cash_color = Color(0XFF71757E)
val balance_dark_savings_color = Color(0XFF7D7960)
val balance_dark_electronic_wallet_color = Color(0XFF627F6F)
val groceries_light = Color(0xFFFFE5D8)
val utilities_light = Color(0xFFD8E9FF)
val transport_light = Color(0xFFD8FFEA)
val rent_light = Color(0xFFFFD8D8)
val shopping_light = Color(0xFFFFFAD8)
val fun_light = Color(0xFFD8FFF4)
val health_light = Color(0xFFFFD8EB)
val gifts_light = Color(0xFFEBFFD8)
val cinema_light = Color(0xFFE2D8FF)
val restaurants_light = Color(0xFFFFE9D8)

val salary_light = Color(0xFFD8F4FF)
val business_light = Color(0xFFEAD8FF)
val freelance_light = Color(0xFFFFD8F2)
val investments_light = Color(0xFFD8FFD5)
val rent_income_light = Color(0xFFFAFFD8)
val dividends_light = Color(0xFFD8E5FF)
val gifts_income_light = Color(0xFFFFF0D8)

val groceries_dark = Color(0xFF7D5A50)
val utilities_dark = Color(0xFF50597D)
val transport_dark = Color(0xFF507D69)
val rent_dark = Color(0xFF7D5050)
val shopping_dark = Color(0xFF7D6950)
val fun_dark = Color(0xFF507D71)
val health_dark = Color(0xFF7D5060)
val gifts_dark = Color(0xFF607D50)
val cinema_dark = Color(0xFF60507D)
val restaurants_dark = Color(0xFF7D6550)

val salary_dark = Color(0xFF50747D)
val business_dark = Color(0xFF65507D)
val freelance_dark = Color(0xFF7D5074)
val investments_dark = Color(0xFF507D4D)
val rent_income_dark = Color(0xFF747D50)
val dividends_dark = Color(0xFF505C7D)
val gifts_income_dark = Color(0xFF7D6B50)

@Immutable
data class CustomColorsPalette(
    val balanceCardColor: Color = Color.Unspecified,
    val balanceCashColor: Color = Color.Unspecified,
    val balanceSavingsColor: Color = Color.Unspecified,
    val balanceEWalletColor: Color = Color.Unspecified,
    val groceriesColor: Color = Color.Unspecified,
    val utilitiesColor: Color = Color.Unspecified,
    val transportColor: Color = Color.Unspecified,
    val rentColor: Color = Color.Unspecified,
    val shoppingColor: Color = Color.Unspecified,
    val funColor: Color = Color.Unspecified,
    val healthColor: Color = Color.Unspecified,
    val giftsColor: Color = Color.Unspecified,
    val cinemaColor: Color = Color.Unspecified,
    val restaurantsColor: Color = Color.Unspecified,
    val salaryColor: Color = Color.Unspecified,
    val businessColor: Color = Color.Unspecified,
    val freelanceColor: Color = Color.Unspecified,
    val investmentsColor: Color = Color.Unspecified,
    val rentIncomeColor: Color = Color.Unspecified,
    val dividendsColor: Color = Color.Unspecified,
    val giftsIncomeColor: Color = Color.Unspecified
)

val LightCustomColorsPalette = CustomColorsPalette(
    balanceCardColor = balance_light_card_color,
    balanceCashColor = balance_light_cash_color,
    balanceSavingsColor = balance_light_savings_color,
    balanceEWalletColor = balance_light_electronic_wallet_color,
    groceriesColor = groceries_light,
    utilitiesColor = utilities_light,
    transportColor = transport_light,
    rentColor = rent_light,
    shoppingColor = shopping_light,
    funColor = fun_light,
    healthColor = health_light,
    giftsColor = gifts_light,
    cinemaColor = cinema_light,
    restaurantsColor = restaurants_light,
    salaryColor = salary_light,
    businessColor = business_light,
    freelanceColor = freelance_light,
    investmentsColor = investments_light,
    rentIncomeColor = rent_income_light,
    dividendsColor = dividends_light,
    giftsIncomeColor = gifts_income_light
)

val DarkCustomColorsPalette = CustomColorsPalette(
    balanceCardColor = balance_dark_card_color,
    balanceCashColor = balance_dark_cash_color,
    balanceSavingsColor = balance_dark_savings_color,
    balanceEWalletColor = balance_dark_electronic_wallet_color,
    groceriesColor = groceries_dark,
    utilitiesColor = utilities_dark,
    transportColor = transport_dark,
    rentColor = rent_dark,
    shoppingColor = shopping_dark,
    funColor = fun_dark,
    healthColor = health_dark,
    giftsColor = gifts_dark,
    cinemaColor = cinema_dark,
    restaurantsColor = restaurants_dark,
    salaryColor = salary_dark,
    businessColor = business_dark,
    freelanceColor = freelance_dark,
    investmentsColor = investments_dark,
    rentIncomeColor = rent_income_dark,
    dividendsColor = dividends_dark,
    giftsIncomeColor = gifts_income_dark
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }


