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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iessochoa.sergiocontreras.kebab_kiosc.data.ToppingRepository
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.MeatType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.Topping
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.BreadSelector
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.MeatSelector
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.components.ToppingsList
import net.iessochoa.sergiocontreras.kebab_kiosc.ui.theme.KebabkioskTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier
) {

    // No es inyección de dependencias pero cumple
    val toppings = ToppingRepository.getAllToppings()

    // --- ESTADO HARDCODEADO ---
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
            toppings = toppings,
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

        // TODO: (Recuperación - 1 punto) Centra este botón horizontalmente
        // TODO: (Recuperación - 6 puntos) Reemplaza este botón con el componente PrimaryButton reutilizable
        //  Debe mostrar "Confirmar Pedido" y ejecutar la acción correspondiente
        Button(
            onClick = { },
        ) {
            Text("Confirmar Pedido")
        }


    }
}

@Composable
private fun SectionTitle(text: String) {
    // TODO: (Recuperación - 1.5 puntos) Centra este título horizontalmente
    //  usando el modifier apropiado para que todos los títulos de sección queden centrados
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    KebabkioskTheme() {
        OrderScreen()
    }
}

