package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.R
import net.iessochoa.sergiocontreras.kebab_kiosc.model.AllergenType


// --- CÓDIGO NUEVO Y MODIFICADO ---

// Define los tipos de alérgenos para que sea más fácil de manejar

/**
 * Muestra una barra horizontal con los alérgenos presentes en el pedido.
 *
 * @param allergens Lista de `AllergenType` a mostrar.
 */
@Composable
fun AllergensBar(allergens: List<AllergenType>, modifier: Modifier = Modifier) {
    if (allergens.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Alérgenos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                allergens.forEach { allergen ->
                    AllergenIcon(allergen = allergen)
                }
            }
        }
    }
}

@Composable
private fun AllergenIcon(allergen: AllergenType) {
    // Aquí asignas el icono correspondiente a cada alérgeno.
    val (icon, label, color) = when (allergen) {
        AllergenType.LACTEOS -> Triple(
            painterResource(R.drawable.milk), // Placeholder
            "Lácteos",
            Color(0xFF64B5F6) // Azul claro
        )
        AllergenType.PICANTE -> Triple(
            painterResource(R.drawable.spicy), // Placeholder
            "Picante",
            Color(0xFFE57373) // Rojo claro
        )
        AllergenType.HUEVO -> Triple(
            painterResource(R.drawable.egg), // Placeholder
            "Huevo",
            Color(0xFFFFD54F) // Amarillo
        )
        AllergenType.FRUTOS_SECOS -> Triple(
            painterResource(R.drawable.peanut), // Placeholder
            "Frutos Secos",
            Color(0xFFA1887F) // Marrón claro
        )
        AllergenType.MARISCOS -> Triple(
            painterResource(R.drawable.shrimp), // Placeholder
            "Mariscos",
            Color(0xFF4DB6AC) // Teal
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Preview(showBackground = true, name = "Allergens Bar Preview")
@Composable
fun AllergensBarPreview() {
    // Muestra todos los alérgenos para la previsualización
    val allAllergens = listOf(
        AllergenType.LACTEOS,
        AllergenType.PICANTE,
        AllergenType.HUEVO,
        AllergenType.FRUTOS_SECOS,
        AllergenType.MARISCOS
    )
    AllergensBar(allergens = allAllergens)
}
