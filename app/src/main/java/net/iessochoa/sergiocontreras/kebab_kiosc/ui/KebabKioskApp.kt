package net.iessochoa.sergiocontreras.kebab_kiosc.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.KebabTopAppBar
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.navigation.KebabKioskNavGraph
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KebabKioskApp(
    navController: NavHostController = rememberNavController(), // Creamos el Controller
){


    /* El bloque del título condicional no hace falta en el examen.
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val currentScreenTitle = when (currentRoute) {
        RecipeListDestination.route -> stringResource(RecipeListDestination.titleRes)
        RecipeDetailDestination.route -> stringResource(RecipeDetailDestination.titleRes)
        else -> stringResource(R.string.app_name)
    }
    */

    // Pero el botón si que tiene que ser condicional a que la pila de navegación no vacía.
    val canNavigateBack = navController.previousBackStackEntry != null


    KebabkioskTheme {
        // TODO: (Recuperación - 1.5 puntos) Añade un padding global y consistente aquí
        //  para que toda la aplicación tenga márgenes profesionales.
        //  Aplícalo desde este Scaffold para no repetirlo en cada pantalla (DRY).
        Scaffold(
            //Lo podríamos dejar feo o ocultar la topAppBar en la primera screen
            topBar = {
                if (canNavigateBack)
                    KebabTopAppBar(
                        title = "",
                        canNavigateBack = canNavigateBack,
                        navigateUp = {navController.navigateUp()}
            ) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
        ) { innerPadding ->
            KebabKioskNavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}