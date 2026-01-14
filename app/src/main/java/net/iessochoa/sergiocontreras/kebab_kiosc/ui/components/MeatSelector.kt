package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iessochoa.sergiocontreras.kebab_kiosc.model.MeatType
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

/**
 * Selector vertical de carnes.
 *
 * Muestra una lista de opciones (`RadioButton` + texto) construidas a partir
 * de la colección `meats`. No mantiene estado interno: la opción seleccionada
 * se comunica mediante el parámetro `selectedMeat` y la lambda `onMeatSelected`.
 *
 * Detalles de comportamiento:
 * - Cada fila ocupa el ancho completo y tiene una altura fija (56.dp).
 * - La fila es `selectable` para soportar interacción y accesibilidad; el
 *   `RadioButton` se marca según `selectedMeat == meat`.
 * - No realiza formateo adicional ni operaciones caras; es una vista simple y
 *   reutilizable que delega la gestión del estado al llamador.
 *
 * @param meats Lista de `MeatType` a mostrar como opciones.
 * @param selectedMeat La carne actualmente seleccionada (para marcar el radio correspondiente).
 * @param onMeatSelected Lambda que se invoca cuando el usuario selecciona una opción;
 *                       recibe el `MeatType` seleccionado.
 * @param modifier Modifier opcional para personalizar el layout desde el exterior.
 */
@Composable
fun MeatSelector(
    meats: List<MeatType>,
    selectedMeat: MeatType,
    onMeatSelected: (MeatType) -> Unit,
    modifier: Modifier = Modifier
) {

    Column (
        modifier = modifier
    )
    {
        meats.forEach { meat ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (selectedMeat == meat),
                        onClick = { onMeatSelected(meat) }
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedMeat == meat),
                    onClick = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = meat.displayName, style = MaterialTheme.typography.bodyLarge, fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MeatSelectorPreview() {
    KebabkioskTheme() {
        MeatSelector(
            meats = MeatType.entries,
            selectedMeat = MeatType.MIXED,
            onMeatSelected = {}
        )

    }
}