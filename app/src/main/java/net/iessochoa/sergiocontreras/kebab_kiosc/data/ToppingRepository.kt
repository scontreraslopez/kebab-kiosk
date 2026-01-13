package net.iessochoa.sergiocontreras.kebab_kiosc.data

import net.iessochoa.sergiocontreras.kebab_kiosc.model.AllergenType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.Topping
import kotlin.random.Random
import kotlin.random.nextInt


object ToppingRepository {
    private val toppings = listOf(
        Topping(
            id = "white_sauce",
            name = "Salsa Blanca",
            price = 0.0,
            allergens = listOf(
                AllergenType.LACTEOS
            )
        ),
        Topping(
            id = "red_sauce",
            name = "Salsa Roja (Picante)",
            price = 0.0,
            allergens = listOf(
                AllergenType.PICANTE
            )
        ),
        Topping(
            id = "eggs",
            name = "Huevo",
            price = 0.50,
            allergens = listOf(
                AllergenType.HUEVO
            )
        ),
        Topping(
            id = "pistachios",
            name = "Pistachos (Crujiente)",
            price = 1.20,
            allergens = listOf(
                AllergenType.FRUTOS_SECOS
            )
        ),
        Topping(
            id = "prawns",
            name = "Gambas (Mar y Montaña)",
            price = 2.00,
            allergens = listOf(
                AllergenType.MARISCOS
            )
        )
    )

    // Retorna copia defensiva de la lista de toppings
    fun getAllToppings() = toppings.toList()
    fun getRandomTopping() = toppings[Random.nextInt(toppings.size)]


}