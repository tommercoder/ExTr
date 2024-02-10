package app.extr.utils.helpers.resproviders

import app.extr.R
import app.extr.ui.theme.CustomColorsPalette

class ExpenseTypesRes(customColorsPalette: CustomColorsPalette) : ResProvider() {
    override val predefinedAttributes by lazy {
        mapOf(
            0 to ResIconColor(R.drawable.egg_alt_icon, customColorsPalette.groceriesColor),
            1 to ResIconColor(R.drawable.build_icon, customColorsPalette.utilitiesColor),
            2 to ResIconColor(R.drawable.directions_bus_icon, customColorsPalette.transportColor),
            3 to ResIconColor(R.drawable.supervisor_account_icon, customColorsPalette.rentColor),
            4 to ResIconColor(R.drawable.universal_currency_icon, customColorsPalette.shoppingColor),
            5 to ResIconColor(R.drawable.mood_icon, customColorsPalette.funColor),
            6 to ResIconColor(R.drawable.monitor_heart_icon, customColorsPalette.healthColor),
            7 to ResIconColor(R.drawable.featured_seasonal_and_gifts_icon, customColorsPalette.giftsColor),
            8 to ResIconColor(R.drawable.movie_icon, customColorsPalette.cinemaColor),
            9 to ResIconColor(R.drawable.restaurant_icon, customColorsPalette.restaurantsColor),
        )
    }
}

class IncomeTypesRes(customColorsPalette: CustomColorsPalette) : ResProvider() {
    override val predefinedAttributes by lazy {
        mapOf(
            0 to ResIconColor(R.drawable.paid_icon, customColorsPalette.salaryColor),
            1 to ResIconColor(R.drawable.business_center_icon, customColorsPalette.businessColor),
            2 to ResIconColor(R.drawable.freelance_icon, customColorsPalette.freelanceColor),
            3 to ResIconColor(R.drawable.query_stats_icon, customColorsPalette.investmentsColor),
            4 to ResIconColor(R.drawable.supervisor_account_icon, customColorsPalette.rentIncomeColor),
            5 to ResIconColor(R.drawable.price_check_icon, customColorsPalette.dividendsColor),
            6 to ResIconColor(R.drawable.featured_seasonal_and_gifts_icon, customColorsPalette.giftsIncomeColor),
        )
    }
}