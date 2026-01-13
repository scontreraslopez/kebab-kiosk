package net.iessochoa.sergiocontreras.kebab_kiosc.model

import net.iessochoa.sergiocontreras.kebab_kiosc.ui.screens.AllergenType

/**
 * Project: kebab-kiosk
 * From: net.iessochoa.sergiocontreras.kebab_kiosc.model
 * Created by: Contr
 * On: 13/01/2026 at 12:37
 * Creado en Settings -> Editor -> File and Code Templates
 */
data class Topping(
    val id: String,
    val name: String,
    val price: Double = 0.0,
    val allergens: List<AllergenType> = emptyList(),
    val drawableRes: Int? = null
)