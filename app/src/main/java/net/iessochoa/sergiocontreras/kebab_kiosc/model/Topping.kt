package net.iessochoa.sergiocontreras.kebab_kiosc.model

import androidx.annotation.DrawableRes

/**
 * Representa un topping (ingrediente adicional) que se puede añadir al kebab.
 *
 * Un `Topping` contiene metadatos necesarios para la UI y para el cálculo del
 * precio y la gestión de alérgenos.
 *
 * @property id Identificador único del topping (p. ej. para serialización o búsquedas)
 * @property name Nombre legible que se mostrará en la interfaz
 * @property price Precio adicional que aplica este topping (en euros). Valor por defecto 0.0
 * @property allergens Lista de alérgenos asociados a este topping. Utiliza el enum `AllergenType`.
 * @property drawableRes Recurso drawable opcional que representa visualmente el topping. Puede ser null si no hay imagen.
 */
data class Topping(
    val id: String,
    val name: String,
    val price: Double = 0.0,
    val allergens: List<AllergenType> = emptyList(),
    @param:DrawableRes val drawableRes: Int? = null
)