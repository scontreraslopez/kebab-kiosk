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


/**
 * Composable que muestra una barra visual de los alérgenos presentes en un elemento.
 *
 * Renderiza cada alérgeno con un icono circular coloreado y su etiqueta. Solo se muestra
 * si la lista de alérgenos no está vacía.
 *
 * @param allergens Lista de alérgenos a mostrar
 * @param modifier Modificador opcional para personalizar el layout
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

/**
 * Componente privado que renderiza un icono circular para un alérgeno específico.
 *
 * Muestra el icono correspondiente al tipo de alérgeno con un fondo coloreado y su etiqueta.
 * Los colores se asignan según el tipo de alérgeno.
 *
 * @param allergen Tipo de alérgeno a renderizar
 */
@Composable
private fun AllergenIcon(allergen: AllergenType) {
    // Aquí asignas el icono correspondiente a cada alérgeno.
    val (icon, label, color) = when (allergen) {
        AllergenType.DAIRY -> Triple(
            painterResource(R.drawable.milk), // Placeholder
            "Lácteos",
            Color(0xFF64B5F6) // Azul claro
        )
        AllergenType.SPICY -> Triple(
            painterResource(R.drawable.spicy), // Placeholder
            "Picante",
            Color(0xFFE57373) // Rojo claro
        )
        AllergenType.EGG -> Triple(
            painterResource(R.drawable.egg), // Placeholder
            "Huevo",
            Color(0xFFFFD54F) // Amarillo
        )
        AllergenType.NUTS -> Triple(
            painterResource(R.drawable.peanut), // Placeholder
            "Frutos Secos",
            Color(0xFFA1887F) // Marrón claro
        )
        AllergenType.SEAFOOD -> Triple(
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
private fun AllergensBarPreview() {
    // Muestra todos los alérgenos para la previsualización
    val allAllergens = listOf(
        AllergenType.DAIRY,
        AllergenType.SPICY,
        AllergenType.EGG,
        AllergenType.NUTS,
        AllergenType.SEAFOOD
    )
    AllergensBar(allergens = allAllergens)
}
