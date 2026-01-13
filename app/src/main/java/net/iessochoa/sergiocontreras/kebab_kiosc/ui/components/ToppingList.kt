package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.data.ToppingRepository
import net.iessochoa.sergiocontreras.kebab_kiosc.model.Topping
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme


/**
 * Composable que proporciona una lista seleccionable de toppings.
 *
 * Muestra cada topping con su nombre, precio (si aplica) y un checkbox para seleccionar/deseleccionar.
 * El fondo cambia de color cuando el topping está seleccionado.
 *
 * @param toppings Lista de toppings disponibles a mostrar
 * @param selectedToppings Conjunto de toppings actualmente seleccionados
 * @param onToppingToggled Callback invocado cuando se selecciona/deselecciona un topping
 */
@Composable
fun ToppingsList(
    toppings: List<Topping>,
    selectedToppings: Set<Topping>,
    onToppingToggled: (Topping, Boolean) -> Unit
) {

    Column {
        toppings.forEach { topping ->
            val isChecked = selectedToppings.contains(topping)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onToppingToggled(topping, isChecked) }
                    .background(if (isChecked) MaterialTheme.colorScheme.secondaryContainer.copy(alpha=0.3f) else Color.Transparent)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = null
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = topping.name, style = MaterialTheme.typography.bodyLarge)
                    if (topping.price > 0) {
                        Text(
                            text = "+${String.format("%.2f", topping.price)}€",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToppingListPreview() {
    KebabkioskTheme() {
        val toppings = ToppingRepository.getAllToppings()
        ToppingsList(toppings, setOf(toppings.first()), { _, _ -> })
    }
}
