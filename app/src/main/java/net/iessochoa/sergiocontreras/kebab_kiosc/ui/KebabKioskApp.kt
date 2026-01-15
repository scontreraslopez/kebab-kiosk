package net.iessochoa.sergiocontreras.kebab_kiosc.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.MeatType
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.OrderScreen
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.OrderScreenViewModel
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

@Composable
fun KebabKioskApp(
    viewModel: OrderScreenViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBreadSelected = { bread: BreadType -> viewModel.onBreadSelected(bread) }
    val onMeatSelected = { meat: MeatType -> viewModel.onMeatSelected(meat) }

    KebabkioskTheme {
        // TODO: (Recuperación - 1.5 puntos) Añade un padding global y consistente aquí
        //  para que toda la aplicación tenga márgenes profesionales.
        //  Aplícalo desde este Scaffold para no repetirlo en cada pantalla (DRY).
        Scaffold(modifier = Modifier
            .fillMaxSize()
        ) { innerPadding ->
            OrderScreen(
                modifier = Modifier.padding(innerPadding),
                uiState = uiState,
                onBreadSelected = onBreadSelected,
                onMeatSelected = onMeatSelected
            )

        }
    }
}