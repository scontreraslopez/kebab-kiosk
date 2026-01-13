package net.iessochoa.sergiocontreras.kebab_kiosc.model

data class Topping(
    val id: String,
    val name: String,
    val price: Double = 0.0,
    val allergens: List<AllergenType> = emptyList(),
    val drawableRes: Int? = null
)