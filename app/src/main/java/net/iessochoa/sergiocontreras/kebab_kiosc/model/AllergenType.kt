package net.iessochoa.sergiocontreras.kebab_kiosc.model

/**
 * Tipos de alérgenos soportados por la aplicación.
 *
 * Esta enumeración representa los distintos tipos de alérgenos que pueden
 * asociarse a ingredientes (por ejemplo, toppings, carnes o salsas) para
 * poder mostrar información al usuario y filtrar/mostrar avisos en la UI.
 *
 * Uso típico:
 * - Cada ingrediente lleva una o más instancias de `AllergenType`.
 * - Se emplea para generar iconos/etiquetas en pantallas como el resumen de
 *   pedido o la selección de ingredientes.
 */
enum class AllergenType {
    /** Productos lácteos (leche, queso, yogur, etc.). */
    DAIRY,

    /** Ingredientes picantes o que contienen especias fuertes. */
    SPICY,

    /** Huevos o productos derivados del huevo. */
    EGG,

    /** Frutos secos (nueces, almendras, avellanas, etc.). */
    NUTS,

    /** Productos del mar (pescado, marisco). */
    SEAFOOD
}