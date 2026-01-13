package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.R
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

/**
 * Composable que renderiza una tarjeta seleccionable para un tipo de pan.
 *
 * Muestra un icono, título y precio con efectos visuales que cambian según el estado
 * de selección. Cuando está seleccionada, el borde se engruesa y los colores se resaltan.
 *
 * @param modifier Modificador opcional para personalizar el layout
 * @param title Nombre del pan a mostrar
 * @param price Precio formateado como string
 * @param isSelected Indica si la tarjeta está actualmente seleccionada
 * @param icon Icono visual del pan
 * @param onClick Callback invocado cuando se hace click en la tarjeta
 */
@Composable
fun BreadCard(
    modifier: Modifier = Modifier,
    title: String,
    price: String,
    isSelected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = price, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BreadCardPreview() {
    KebabkioskTheme() {
        BreadCard(
            title = "Pita",
            price = "4.50€",
            isSelected = true,
            icon = ImageVector.vectorResource(R.drawable.baseline_fastfood_24),
            onClick = {}
        )
    }
}
