package net.iessochoa.sergiocontreras.kebab_kiosc.ui.navigation

/**
    La solución se orienta a la manera clásica por que es la que más se ha trabajado en clase, tu eres libre de tirar por serializable si te sientes más cómodo por que está importada
    Todas los destinos de navegación implementarán esta interfaz.
 */
interface NavigationDestination {
    val route: String
}