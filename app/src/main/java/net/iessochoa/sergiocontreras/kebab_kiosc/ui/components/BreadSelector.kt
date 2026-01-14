package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

/**
 * Selector visual de panes.
 *
 * Muestra una fila de `BreadCard`, una por cada `BreadType` proporcionado en
 * `breads`. Cada tarjeta muestra el nombre, el icono y el precio (formateado)
 * y es clickable para seleccionar el pan.
 *
 * Comportamiento y detalles de implementación:
 * - Cada `BreadCard` usa `Modifier.weight(1f)` para repartir el espacio entre
 *   las tarjetas de forma equitativa.
 * - El precio se formatea con dos decimales y el símbolo "€".
 * - No mantiene estado interno: la selección se controla desde fuera mediante
 *   el parámetro `selectedBread` y la lambda `onBreadSelected`.
 *
 * @param breads Lista de `BreadType` disponibles a mostrar.
 * @param selectedBread El `BreadType` actualmente seleccionado (para marcar visualmente la tarjeta).
 * @param onBreadSelected Lambda que se invoca cuando el usuario selecciona un pan;
 *                        recibe el `BreadType` seleccionado.
 */
@Composable
fun BreadSelector(
    breads: List<BreadType>,
    selectedBread: BreadType,
    onBreadSelected: (BreadType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        breads.forEach { bread ->
            val priceFormatted = String.format("%.2f €", bread.basePrice)
            BreadCard(
                modifier = Modifier.weight(1f),
                title = bread.name,
                price = priceFormatted,
                isSelected = selectedBread == bread,
                icon = bread.icon,
                onClick = { onBreadSelected(bread) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BreadSelectorPreview() {
    KebabkioskTheme() {
        BreadSelector(
            breads = BreadType.entries,
            selectedBread = BreadType.PITA,
            onBreadSelected = {}
        )
    }
}
