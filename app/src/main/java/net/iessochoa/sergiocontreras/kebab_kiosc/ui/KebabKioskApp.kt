package net.iessochoa.sergiocontreras.kebab_kiosc.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.OrderScreen
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

// Si, he escrito mal el nombre del proyecto kiosc y no kiosk...
// Aquí intento apañarlo

@Composable
fun KebabKioskApp(){
    KebabkioskTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OrderScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}