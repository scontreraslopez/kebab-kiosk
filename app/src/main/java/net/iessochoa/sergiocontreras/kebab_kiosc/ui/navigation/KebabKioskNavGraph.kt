package net.iessochoa.sergiocontreras.kebab_kiosc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.MeatType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.Topping
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.OrderScreen
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.OrderScreenViewModel
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.SummaryScreen


@Composable
fun KebabKioskNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: OrderScreenViewModel = viewModel()       //Como es compartido debe estar aquí
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = AppDestination.OrderScreen.route,
    ) {
        // PANTALLA 1: ORDER SCREEN
        composable(route = AppDestination.OrderScreen.route) {

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val onBreadSelected = { bread: BreadType -> viewModel.onBreadSelected(bread) }
            val onMeatSelected = { meat: MeatType -> viewModel.onMeatSelected(meat) }
            val onToppingToggled = { topping: Topping, isChecked: Boolean -> viewModel.onToppingToggled(topping, isChecked)}

            OrderScreen(
                uiState = uiState,
                onBreadSelected = onBreadSelected,
                onMeatSelected = onMeatSelected,
                onToppingToggled = onToppingToggled,
                onOrderConfirmed = { navController.navigate(AppDestination.SummaryScreen.route) },
                modifier = modifier,
            )
        }

        //PANTALLA 2: SUMMARY SCREEN
        composable(route = AppDestination.SummaryScreen.route) {

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SummaryScreen(
                pan = uiState.selectedBread.toString(),
                carne = uiState.selectedMeat.toString(),
                salsasYExtras = uiState.selectedToppings.toList(),
                totalPedido = viewModel.calculateTotal()
            )


        }



    }
}