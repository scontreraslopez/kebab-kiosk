package net.iessochoa.sergiocontreras.kebab_kiosc.model

import androidx.annotation.DrawableRes
import net.iessochoa.sergiocontreras.kebab_kiosc.R

/**
 * Project: kebab-kiosk
 * From: net.iessochoa.sergiocontreras.kebab_kiosc.model
 * Created by: Contr
 * On: 13/01/2026 at 14:26
 * Creado en Settings -> Editor -> File and Code Templates
 */

enum class BreadType(
    val id: String,
    val displayName: String,
    @param:DrawableRes val icon: Int,
    val basePrice: Double,
) {
    PITA("pita", "Pita", R.drawable.pita, 4.00),
    DURUM("durum", "Dürüm", R.drawable.durum, 5.50)
}
