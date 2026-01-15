package net.iessochoa.sergiocontreras.kebab_kiosc.ui.navigation

sealed class AppDestination(override val route: String) : NavigationDestination {
    data object OrderScreen : AppDestination("order_screen")
    data object SummaryScreen : AppDestination("summary_screen")
}