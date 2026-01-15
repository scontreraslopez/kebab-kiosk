package net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.iessochoa.sergiocontreras.kebab_kiosc.model.BreadType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.MeatType


class OrderScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(OrderScreenUiState())
    val uiState: StateFlow<OrderScreenUiState> = _uiState.asStateFlow()


    fun onBreadSelected(selectedBread: BreadType) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedBread = selectedBread
            )
        }
    }

    fun onMeatSelected(selectedMeat: MeatType) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedMeat = selectedMeat
            )
        }
    }




    fun onToppingToggled() {


    }






}