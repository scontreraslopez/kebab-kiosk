package net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.SummaryItem


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

            }
        }
    }
}