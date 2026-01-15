package net.iessochoa.sergiocontreras.kebab_kiosc.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.navigation.KebabKioskNavGraph
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

@Composable
fun KebabKioskApp(
    navController: NavHostController = rememberNavController() // Creamos el Controller
){

    KebabkioskTheme {
        // TODO: (Recuperación - 1.5 puntos) Añade un padding global y consistente aquí
        //  para que toda la aplicación tenga márgenes profesionales.
        //  Aplícalo desde este Scaffold para no repetirlo en cada pantalla (DRY).
        Scaffold(modifier = Modifier
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