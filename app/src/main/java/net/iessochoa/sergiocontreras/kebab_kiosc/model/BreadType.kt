package net.iessochoa.sergiocontreras.kebab_kiosc.model

/**
 * Project: kebab-kiosk
 * From: net.iessochoa.sergiocontreras.kebab_kiosc.model
 * Created by: Contr
 * On: 13/01/2026 at 14:26
 * Creado en Settings -> Editor -> File and Code Templates
 */

enum class BreadType(val id: String, val displayName: String, val basePrice: Double) {
    PITA("pita", "Pita", 4.00),
    DURUM("durum", "Dürüm", 5.50)
}
