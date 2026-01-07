package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.R
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.BreadType

@Composable
fun BreadSelector(
    selectedBread: BreadType,
    onBreadSelected: (BreadType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BreadCard(
            modifier = Modifier.weight(1f),
            title = "PAN PITA",
            price = "4.00€",
            isSelected = selectedBread == BreadType.PITA,
            icon = ImageVector.vectorResource(R.drawable.baseline_fastfood_24),
            onClick = { onBreadSelected(BreadType.PITA) }
        )
        BreadCard(
            modifier = Modifier.weight(1f),
            title = "DURUM",
            price = "5.50€",
            isSelected = selectedBread == BreadType.DURUM,
            icon = ImageVector.vectorResource(R.drawable.baseline_restaurant_24),
            onClick = { onBreadSelected(BreadType.DURUM) }
        )
    }
}
