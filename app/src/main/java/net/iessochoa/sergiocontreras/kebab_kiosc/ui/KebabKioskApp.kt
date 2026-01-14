package net.iessochoa.sergiocontreras.kebab_kiosc.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.OrderScreen
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

@Composable
fun KebabKioskApp(){
    KebabkioskTheme {
        // TODO: (Recuperación - 1.5 puntos) Añade un padding global y consistente aquí
        //  para que toda la aplicación tenga márgenes profesionales.
        //  Aplícalo desde este Scaffold para no repetirlo en cada pantalla (DRY).
        Scaffold(modifier = Modifier
            .fillMaxSize()
        ) { innerPadding ->
            OrderScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}