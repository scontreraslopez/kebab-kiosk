package net.iessochoa.sergiocontreras.kebab_kiosc.model

import androidx.annotation.DrawableRes
import net.iessochoa.sergiocontreras.kebab_kiosc.R

/**
 * Tipos de pan disponibles para los kebabs.
 *
 * Representa las distintas opciones de pan que puede seleccionar el usuario.
 * Contiene metadatos necesarios para la UI y para el cálculo del precio.
 *
 * @property id Identificador único usado internamente (p. ej. para serialización)
 * @property displayName Nombre legible para mostrar en la interfaz
 * @property icon Recurso drawable que representa visualmente el pan
 * @property basePrice Precio base del pan en euros
 */
enum class BreadType(
    val id: String,
    val displayName: String,
    @param:DrawableRes val icon: Int,
    val basePrice: Double,
) {
    /** Pan tipo Pita. */
    PITA("pita", "Pita", R.drawable.pita, 4.00),

    /** Pan tipo Dürüm. */
    DURUM("durum", "Dürüm", R.drawable.durum, 5.50)
}
