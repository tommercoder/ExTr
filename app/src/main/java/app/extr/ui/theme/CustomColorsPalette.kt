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

@Immutable
data class CustomColorsPalette(
    val balanceCardColor: Color = Color.Unspecified,
    val balanceCashColor: Color = Color.Unspecified,
    val balanceSavingsColor: Color = Color.Unspecified,
    val balanceEWalletColor: Color = Color.Unspecified
)

val LightCustomColorsPalette = CustomColorsPalette(
    balanceCardColor = balance_light_card_color,
    balanceCashColor = balance_light_cash_color,
    balanceSavingsColor = balance_light_savings_color,
    balanceEWalletColor = balance_light_electronic_wallet_color
)

val DarkCustomColorsPalette = CustomColorsPalette(
    balanceCardColor = balance_dark_card_color,
    balanceCashColor = balance_dark_cash_color,
    balanceSavingsColor = balance_dark_savings_color,
    balanceEWalletColor = balance_dark_electronic_wallet_color
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }


