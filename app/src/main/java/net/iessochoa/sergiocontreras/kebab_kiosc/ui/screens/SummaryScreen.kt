package net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens

import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iessochoa.sergiocontreras.kebab_kiosc.R
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioscTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(

) {

    // --- ESTADO HARDCODEADO (El "pecado" que hay que refactorizar) ---
    var selectedBread by remember { mutableStateOf(BreadType.PITA) }
    var selectedMeat by remember { mutableStateOf(MeatType.MIXED) }
    var selectedToppings by remember { mutableStateOf(emptySet<Topping>()) }

    // --- LÓGICA DE NEGOCIO INCRUSTADA EN LA UI ---
    // Calculamos el precio en cada recomposición (ineficiente pero funciona)
    val breadPrice = selectedBread.price
    val toppingsPrice = selectedToppings.sumOf { it.price }
    val totalPrice = breadPrice + toppingsPrice


    // 1. SECCIÓN PAN
    SectionTitle("1. Elige tu Pan")
    BreadSelectionRow(
        selectedBread = selectedBread,
        onBreadSelected = { newBread ->
            selectedBread = newBread // Modificación directa del estado
        }
    )

    HorizontalDivider()

    // 2. SECCIÓN CARNE
    SectionTitle("2. Elige la Carne")
    MeatSelectionColumn(
        selectedMeat = selectedMeat,
        onMeatSelected = { newMeat ->
            selectedMeat = newMeat // Modificación directa
        }
    )

    HorizontalDivider()

    // 3. SECCIÓN SALSAS Y EXTRAS
    SectionTitle("3. Salsas y Extras")
    ToppingsList(
        selectedToppings = selectedToppings,
        onToppingToggled = { topping, isChecked ->
            // Lógica de conjuntos "a mano" dentro de la vista
            val currentSet = selectedToppings.toMutableSet()
            if (isChecked) {
                currentSet.remove(topping) // Si ya estaba, lo quitamos (toggle)
            } else {
                currentSet.add(topping) // Si no, lo ponemos
            }
            selectedToppings = currentSet // Actualizamos estado
        }
    )

    Spacer(modifier = Modifier.height(60.dp))
}

// --- COMPONENTES UI (Iguales que antes) ---

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun BreadSelectionRow(
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

@Composable
fun MeatSelectionColumn(
    selectedMeat: MeatType,
    onMeatSelected: (MeatType) -> Unit
) {
    val meats = listOf(
        MeatType.VEAL to "Ternera",
        MeatType.CHICKEN to "Pollo",
        MeatType.MIXED to "Mixto"
    )

    Column {
        meats.forEach { (type, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (selectedMeat == type),
                        onClick = { onMeatSelected(type) }
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedMeat == type),
                    onClick = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = label, style = MaterialTheme.typography.bodyLarge, fontSize = 18.sp)
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    KebabkioscTheme() {
        OrderScreen()
    }
}


// --- MODELOS DE DATOS (Hardcodeados aquí mismo para el caos) ---

enum class BreadType(val price: Double) {
    PITA(4.50),
    DURUM(5.50)
}

enum class MeatType { VEAL, CHICKEN, MIXED }

enum class Topping(val price: Double) {
    SAUCE_WHITE(0.0),
    SAUCE_RED(0.0),
    EGG(0.50),
    PISTACHIOS(1.20),
    PRAWNS(2.00)
}