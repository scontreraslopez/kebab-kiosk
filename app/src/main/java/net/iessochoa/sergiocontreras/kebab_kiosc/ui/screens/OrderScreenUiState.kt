package net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.MeatType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.Topping


data class OrderScreenUiState (

    val selectedBread: BreadType = BreadType.PITA,
    val selectedMeat : MeatType = MeatType.MIXED,
    val selectedToppings: Set<Topping> = emptySet()

)