package net.iessochoa.sergiocontreras.kebab_kiosc.model

/**
 * Tipos de carne disponibles para los kebabs.
 *
 * Enum que representa las opciones de carne que puede elegir el usuario.
 * Cada constante incluye un identificador interno (`id`) y un nombre para
 * mostrar (`displayName`). Se usan como catálogo de metadatos desde la UI.
 *
 * @property id Identificador único (p. ej. para serialización o búsqueda)
 * @property displayName Nombre legible para mostrar en la interfaz
 */
enum class MeatType(val id: String, val displayName: String) {
    /** Carne de ternera. */
    BEEF("beef", "Ternera"),

    /** Carne de pollo. */
    CHICKEN("chicken", "Pollo"),

    /** Mezcla de ternera y pollo. */
    MIXED("mixed", "Mixto")
}