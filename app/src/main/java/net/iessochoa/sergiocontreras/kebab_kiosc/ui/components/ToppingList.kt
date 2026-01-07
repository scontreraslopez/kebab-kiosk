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
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.Topping


@Composable
fun ToppingsList(
    selectedToppings: Set<Topping>,
    onToppingToggled: (Topping, Boolean) -> Unit
) {
    val allToppings = Topping.values()

    Column {
        allToppings.forEach { topping ->
            val isChecked = selectedToppings.contains(topping)
            val name = when(topping) {
                Topping.SAUCE_WHITE -> "Salsa Blanca"
                Topping.SAUCE_RED -> "Salsa Roja (Picante)"
                Topping.EGG -> "Huevo"
                Topping.PISTACHIOS -> "Pistachos (Crujiente)"
                Topping.PRAWNS -> "Gambas (Mar y Montaña)"
            }

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
                    Text(text = name, style = MaterialTheme.typography.bodyLarge)
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