package net.iessochoa.sergiocontreras.kebab_kiosc.data

import net.iessochoa.sergiocontreras.kebab_kiosc.model.AllergenType
import net.iessochoa.sergiocontreras.kebab_kiosc.model.Topping
import kotlin.random.Random

/**
 * Repositorio sencillo (singleton) que expone la lista de toppings disponibles.
 *
 * Este objeto actúa como un catálogo en memoria de `Topping` que la UI puede
 * consultar. Está pensado para ser una fuente de datos inmutable y sin estado,
 * útil durante el desarrollo o para ejemplos/datos hardcodeados.
 *
 * Notas de diseño:
 * - `toppings` es privado y sólo se accede a través de métodos públicos.
 * - `getAllToppings()` devuelve una copia defensiva (`toList()`) para evitar que
 *   el consumidor modifique la colección interna.
 * - `getRandomTopping()` selecciona un elemento aleatorio; asume que la lista
 *   no está vacía (si la vacías, lanzará `IndexOutOfBoundsException`).
 */
object ToppingRepository {
    private val toppings = listOf(
        Topping(
            id = "white_sauce",
            name = "Salsa Blanca",
            price = 0.0,
            allergens = listOf(
                AllergenType.DAIRY
            )
        ),
        Topping(
            id = "red_sauce",
            name = "Salsa Roja (Picante)",
            price = 0.0,
            allergens = listOf(
                AllergenType.SPICY
            )
        ),
        Topping(
            id = "eggs",
            name = "Huevo",
            price = 0.50,
            allergens = listOf(
                AllergenType.EGG
            )
        ),
        Topping(
            id = "pistachios",
            name = "Pistachos (Crujiente)",
            price = 1.20,
            allergens = listOf(
                AllergenType.NUTS
            )
        ),
        Topping(
            id = "prawns",
            name = "Gambas (Mar y Montaña)",
            price = 2.00,
            allergens = listOf(
                AllergenType.SEAFOOD
            )
        )
    )

    /**
     * Retorna una lista con todos los toppings disponibles.
     *
     * Devuelve una copia defensiva para mantener la inmutabilidad del repositorio.
     * @return lista de `Topping`
     */
    fun getAllToppings(): List<Topping> = toppings.toList()

    /**
     * Retorna un `Topping` aleatorio de la lista.
     *
     * Aviso: si `toppings` estuviera vacío lanzaría una excepción. En este caso
     * la lista está inicializada con elementos estáticos, por lo que no ocurre.
     * @return un topping aleatorio
     */
    fun getRandomTopping(): Topping = toppings[Random.nextInt(toppings.size)]

}