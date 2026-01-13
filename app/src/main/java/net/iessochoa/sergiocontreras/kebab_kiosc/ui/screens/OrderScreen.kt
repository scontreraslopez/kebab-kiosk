package net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.MeatType
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.BreadSelector
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.MeatSelector
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.SectionTitle
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.ToppingsList
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier
) {
    // --- ESTADO HARDCODEADO (El "pecado" que hay que refactorizar) ---
    var selectedBread by remember { mutableStateOf(BreadType.PITA) }
    var selectedMeat by remember { mutableStateOf(MeatType.MIXED) }
    var selectedToppings by remember { mutableStateOf(emptySet<Topping>()) }

    // --- LÓGICA DE NEGOCIO INCRUSTADA EN LA UI ---
    // Calculamos el precio en cada recomposición (ineficiente pero funciona)
    val breadPrice = selectedBread.basePrice
    val toppingsPrice = selectedToppings.sumOf { it.price }
    val totalPrice = breadPrice + toppingsPrice

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 1. SECCIÓN PAN
        SectionTitle("1. Elige tu Pan")
        BreadSelector(
            breads = BreadType.entries,
            selectedBread = selectedBread,
            onBreadSelected = { newBread ->
                selectedBread = newBread // Modificación directa del estado
            }
        )

        Spacer(modifier = Modifier.padding(top=16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(top=16.dp))


        // 2. SECCIÓN CARNE
        SectionTitle("2. Elige la Carne")
        MeatSelector(
            meats = MeatType.entries,
            selectedMeat = selectedMeat,
            onMeatSelected = { newMeat ->
                selectedMeat = newMeat
            }
        )

        Spacer(modifier = Modifier.padding(top=16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(top=16.dp))

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


        Spacer(modifier = Modifier.padding(top=16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(top=16.dp))

        Button(
            onClick = { },
        ) {
            Text("Confirmar Pedido")
        }



    }
}


@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    KebabkioskTheme() {
        OrderScreen()
    }
}


// --- MODELOS DE DATOS (Hardcodeados aquí mismo para el caos) ---

/*enum class BreadType(val price: Double) {
    PITA(4.50),
    DURUM(5.50)
}
*/
//enum class MeatType { VEAL, CHICKEN, MIXED }
/*
enum class Topping(val price: Double) {
    SAUCE_WHITE(0.0),
    SAUCE_RED(0.0),
    EGG(0.50),
    PISTACHIOS(1.20),
    PRAWNS(2.00)
}
*/