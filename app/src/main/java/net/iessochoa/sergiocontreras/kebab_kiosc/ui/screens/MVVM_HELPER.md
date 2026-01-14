# 🧠 MVVM HELPER (Guía de Supervivencia)

Si te quedas atacasdo con el MVV, sigue esta "receta de cocina" para implementar la arquitectura **MVVM** (Model-View-ViewModel) y gestionar el estado de tu aplicación de forma profesional, tal y como vimos en el tema T07.

> **Ejemplo Práctico:** Usaremos como referencia una **App de Recetas** donde tenemos una lista de platos y necesitamos gestionar cuál ha seleccionado el usuario para ver su detalle.

-----

### 🥘 Paso 1: La Clase de Estado (`UiState`)

Define **QUÉ** datos necesita mostrar tu pantalla. Crea una `data class` (normalmente en un archivo separado `RecipeUiState.kt`).

**Reglas de Oro:**

* Debe ser una `data class`.
* Usa `val` (inmutable).
* Proporciona valores por defecto para todo.

```kotlin
// Asumimos que tienes un modelo 'Recipe' definido en tu capa data/model
data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(), // La lista completa para el menú
    val selectedRecipe: Recipe? = null,      // El plato que se está cocinando/viendo
    val isLoading: Boolean = false           // ¿Estamos esperando ingredientes?
)
```

-----

### 🥘 Paso 2: El `ViewModel` (El Chef)

Crea tu clase `RecipeViewModel.kt` heredando de `ViewModel`. Aquí es donde ocurre la magia de la cocina.

Necesitas configurar dos propiedades clave:

1.  **`_uiState` (Privada y Mutable):** Tu olla interna donde cocinas, mezclas y pruebas.
2.  **`uiState` (Pública e Inmutable):** La ventanilla de pase por la que la UI mira el resultado.

```kotlin
class RecipeViewModel : ViewModel() {

    // 1. ESTADO INTERNO (MutableStateFlow)
    // Inicializamos con la lista vacía o cargamos datos iniciales
    private val _uiState = MutableStateFlow(RecipeUiState())

    // 2. ESTADO PÚBLICO (StateFlow - Solo lectura)
    // Exponemos el flujo para que la UI se suscriba sin poder tocar la olla
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    // 3. EVENTOS (Lógica de Negocio)
    // Funciones que la UI llamará cuando el comensal (usuario) interactúe
    
    // Ejemplo: El usuario hace clic en una receta de la lista
    fun onRecipeSelected(recipe: Recipe) {
        _uiState.update { currentState ->
            // Usamos .copy() para servir un nuevo estado con la selección actualizada
            currentState.copy(
                selectedRecipe = recipe
            )
        }
    }
    
    // Ejemplo: Limpiar la selección al volver atrás
    fun onBackToMenu() {
         _uiState.update { it.copy(selectedRecipe = null) }
    }
}
```

-----

### 🥘 Paso 3: La UI (`Composable`)

Conecta tu pantalla al cerebro. La UI **no piensa**, solo sirve los platos (pinta) y toma nota de las comandas (eventos).

**Puntos Clave:**

* Inyecta el ViewModel (`viewModel()` en la firma).
* Convierte el flujo en estado (`collectAsState()`).
* Pasa los datos hacia abajo (State Hoisting).
* Pasa los eventos hacia arriba (Lambdas).

```kotlin
@Composable
fun RecipeApp(
    // 1. Obtener la instancia del Chef (ViewModel)
    viewModel: RecipeViewModel = viewModel() 
) {
    // 2. OBSERVAR EL ESTADO
    // Convertimos el flujo StateFlow en una variable de estado de Compose
    val uiState by viewModel.uiState.collectAsState()

    // 3. PINTAR LA PANTALLA (Navegación simple o lógica de visualización)
    // Si hay receta seleccionada mostramos detalle, si no, la lista.
    if (uiState.selectedRecipe != null) {
        RecipeDetailScreen(
            recipe = uiState.selectedRecipe, // Pasamos DATOS
            onBack = { viewModel.onBackToMenu() } // Pasamos EVENTOS
        )
    } else {
        RecipeListScreen(
            // Pasamos DATOS (la lista de recetas)
            recipeList = uiState.recipes,
            
            // Pasamos EVENTOS (acciones al ViewModel)
            onRecipeClick = { recipe -> 
                viewModel.onRecipeSelected(recipe) 
            }
        )
    }
}

// Componente "tonto" (Stateless) - Solo recibe datos y lambdas
@Composable
fun RecipeListScreen(
    recipeList: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit
) {
    // Aquí va tu LazyColumn mostrando las recetas.
    // Cuando pulsen una: .clickable { onRecipeClick(it) }
}
```

-----

### 💡 Resumen del Flujo de Datos (Unidireccional)

1.  El **Usuario** pulsa una receta en la lista ("¡Quiero esa!"). 👆
2.  La **UI** llama a `viewModel.onRecipeSelected(receta)`.
3.  El **ViewModel** actualiza el `_uiState` guardando esa receta en `selectedRecipe` usando `.update { ... }`.
4.  El **StateFlow** emite el nuevo menú (estado).
5.  La **UI** (que está observando con `collectAsState`) recibe el cambio y se **Recompone** (muestra la pantalla de detalle) automáticamente 🎨.

Aquí tienes la nota resumen redactada con un tono claro y directo para que los alumnos sepan a qué atenerse y elijan la opción que más seguridad les dé.

Puedes adjuntarla al final del enunciado o proyectarla en la pizarra antes de empezar.

-----


# 💡 Nota Importante: Gestión del ViewModel en la UI

Para la implementación de las pantallas (`Screens`) en este examen, tienes dos opciones arquitectónicas válidas. Elige la que te resulte más cómoda:

## Opción A: Pantallas "Inteligentes" (Simplificación para el examen)

Puedes pasar el `OrderScreenViewModel` directamente como parámetro a tus pantallas principales (`OrderScreen` y `SummaryScreen`). Esto es lo mismo que se hizo en el ejemplo de los goblins y las arañas.

* **Ventaja:** Simplifica mucho el código en el `NavHost` (`KebabKioskApp.kt`), ya que no tienes que declarar un montón de lambdas para cada evento.
* **Desventaje** La pantalla queda "acoplada" con ese ViewModel. Es más difícil de previsualizar (@Preview) porque el preview no tiene ViewModel.
* **Cómo se hace:**

```kotlin
  // En PatientListScreen.kt
  @Composable
  fun PatientListScreen(
      viewModel: DoctorViewModel, // <-- Recibe el VM
      onNavigateToDetail: () -> Unit
  ) {
      val uiState by viewModel.uiState.collectAsState()
      // ... usa uiState y llama a viewModel.onPatientSelected(...)
  }
```

## Opción B: Pantallas "Tontas" (State Hoisting Puro)

Puedes hacer que tus pantallas solo reciban el estado (`OrderScreenUiState` o datos sueltos) y funciones lambda para los eventos.

* **Ventaja:** Es una arquitectura más pura y reutilizable, ideal para Previews.
* **Desventaja:** Tendrás que escribir más código de conexión en el `NavHost`.
* **Cómo se hace:**

```kotlin
  // En PatientListScreen.kt
  @Composable
  fun PatientListScreen(
      patients: List<Patient>,      // <-- Solo datos
      onPatientClick: (Patient) -> Unit // <-- Solo eventos
  ) { ... }
```

```kotlin
NavHost(navController = navController, startDestination = "list") {

    composable("list") {
        // 1. Tienes que observar el estado AQUÍ (en el NavHost)
        // para poder pasárselo a la pantalla tonta.
        val uiState by viewModel.uiState.collectAsState()

        // 2. Llamas a la pantalla pasando dato a dato y evento a evento
        PatientListScreen(
            // DATOS: Sacas la lista del uiState y se la pasas
            patients = uiState.patients,

            // EVENTOS: Defines aquí qué pasa cuando hacen click
            onPatientClick = { patient ->
                viewModel.onPatientSelected(patient) // Avisas al ViewModel
                navController.navigate("detail")     // Haces la navegación
            }
        )
    }
    
    // ... resto de rutas
}
```
-----

## ⚠️ REGLA DE ORO (Para ambas opciones):

Independientemente de la opción que elijas para las Pantallas, los **componentes pequeños y reutilizables** (como `AllergensBar`, `BreadCard`, `MeatSelector`, etc.) **NUNCA deben recibir el `ViewModel`**.

* ✅ **Correcto:** `fun BreadCard(bread: Bread, onSelect: (Bread) -> Unit)`
* ❌ **Incorrecto:** `fun BreadCard(viewModel: OrderScreenViewModel, onSelect: () -> Unit)`