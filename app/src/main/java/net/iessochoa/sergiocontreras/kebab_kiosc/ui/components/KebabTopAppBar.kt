package net.iessochoa.sergiocontreras.kebab_kiosc.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import net.iessochoa.sergiocontreras.kebab_kiosc.R

/**
 * Barra superior (Top App Bar) de la app.
 *
 * Muestra un título centrado y, opcionalmente, un botón de navegación "atrás".
 * Está pensada para usarse en pantallas principales como `OrderScreen` y
 * `SummaryScreen`, integrándose con un `Scaffold`.
 *
 * Regla práctica:
 * - Si la pantalla es el punto inicial del flujo (home), usa `canNavigateBack = false`.
 * - Si la pantalla está en una ruta secundaria, usa `canNavigateBack = true` y proporciona
 *   `navigateUp` con `navController::navigateUp` o `navController::popBackStack`.
 *
 * @param title Texto del título que se muestra centrado.
 * @param canNavigateBack Si es `true`, se renderiza el icono de volver atrás.
 * @param modifier Modifier opcional para personalizar el layout desde el exterior.
 * @param scrollBehavior Comportamiento de scroll del AppBar (por ejemplo, para colapsar con un LazyColumn).
 * @param navigateUp Acción que se ejecuta al pulsar el botón de volver atrás.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KebabTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Volver atrás"
                    )
                }
            }
        }
    )
}
