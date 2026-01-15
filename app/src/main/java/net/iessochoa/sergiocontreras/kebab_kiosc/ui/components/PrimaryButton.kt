package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(8.dp))
    }

}