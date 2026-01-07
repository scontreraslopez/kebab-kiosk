package net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iessochoa.sergiocontreras.kebab_kiosc.R

@Composable
fun OrderSummary(
    pan: String,
    carne: String,
    salsasYExtras: List<String>,
    modifier: Modifier = Modifier // Añadimos un modifier para más flexibilidad
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Resumen de tu Pedido",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Sección de Pan
            SummaryItem(label = "Pan", value = pan)

            Spacer(modifier = Modifier.height(8.dp))

            // Sección de Carne
            SummaryItem(label = "Carne", value = carne)

            Spacer(modifier = Modifier.height(8.dp))

            // Sección de Salsas y Extras
            val extrasString = if (salsasYExtras.isNotEmpty()) {
                salsasYExtras.joinToString(separator = ", ")
            } else {
                "Ninguno"
            }
            SummaryItem(label = "Salsas y extras", value = extrasString)
        }
    }
}

@Composable
private fun SummaryItem(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Preview para ver el resultado en Android Studio
@Preview(showBackground = true)
@Composable
fun OrderSummaryPreview() {
    val panEjemplo = "Pan de pita"
    val carneEjemplo = "Pollo"
    val extrasEjemplo = listOf("Salsa de yogur", "Lechuga", "Cebolla", "Queso feta")

    OrderSummary(
        pan = panEjemplo,
        carne = carneEjemplo,
        salsasYExtras = extrasEjemplo
    )
}

// --- CÓDIGO NUEVO Y MODIFICADO ---

// Define los tipos de alérgenos para que sea más fácil de manejar
enum class AllergenType {
    LACTEOS, PICANTE, HUEVO, FRUTOS_SECOS, MARISCOS
}

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


@Composable
fun BottomOrderBar(
    totalPrice: Double,
    onNextClick: () -> Unit
) {
    Surface(
        shadowElevation = 16.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "TOTAL", style = MaterialTheme.typography.labelMedium)
                Text(
                    text = "${String.format("%.2f", totalPrice)}€",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = onNextClick,
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("RESUMEN", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    ImageVector.vectorResource(R.drawable.baseline_arrow_forward_24),
                    contentDescription = null
                )
            }
        }
    }
}