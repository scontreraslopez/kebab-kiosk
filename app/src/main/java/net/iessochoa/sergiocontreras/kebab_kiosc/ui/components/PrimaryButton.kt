package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * TODO: Componente reutilizable PrimaryButton (Recuperación - 6 puntos)
 *
 * Crea un botón reutilizable que:
 * - Reciba el TEXTO que se mostrará en el botón
 * - Reciba la ACCIÓN que se ejecutará al hacer clic
 * - Acepte un MODIFIER opcional para flexibilidad
 * - Implemente un ESTILO CONSISTENTE (colores, padding, esquinas redondeadas, etc.). Me sirve con fusilarte el estilo del botón hardcodeado en `SummaryScreen`
 *
 * Este componente debe usarse tanto en "Confirmar Pedido" como en "PAGAR".
 *
 * CRITERIO CLAVE: El botón NO debe decidir qué hace, solo recibe la acción como parámetro.
 * Esto demuestra que entiendes ELEVACIÓN DE ESTADO.
 */

// TODO: Implementa aquí el composable PrimaryButton

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {


}