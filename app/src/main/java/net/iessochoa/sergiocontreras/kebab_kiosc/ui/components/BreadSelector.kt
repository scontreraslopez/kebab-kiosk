package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.R
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

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
