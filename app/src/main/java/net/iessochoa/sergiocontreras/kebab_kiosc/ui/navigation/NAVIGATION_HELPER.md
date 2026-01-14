# Navigation Helper

## Classic Edition

Si en algún momento te quedas atascado, puedes ver esta "receta de cocina" paso a paso para implementar la navegación en tu examen usando el patrón clásico de **Interfaces** y **Rutas String**. Similar al de la aplicación de las arañas y los goblins. En este caso está tematizado en una App de recetas de cocina y para aplicaciones de poca complejidad donde nos podemos permitir algunas licencias como un ViewModel compartido.

> **Ejemplo Práctico:** Vamos a usar como referencia una **App de Recetas** que tiene dos pantallas: una **Lista de Platos** y un **Detalle de Receta**.

### 🥘 Ingredientes Previos

1. **Dependencias:** Revisa que en tu `build.gradle.kts` (Module: app) tienes la librería de navegación (normalmente ya viene en el proyecto base):
   `implementation("androidx.navigation:navigation-compose:2.9.X")`
2. **Orden:** Crea un paquete llamado `ui/navigation` para no mezclar ficheros.

Si estás leyendo esto en el examen, puedes estar tranquilo, en general ya lo tendrás todo preparado en el proyecto base.

-----

### 🍳 Paso 1: La Interfaz `NavigationDestination`

Define el "contrato" que deben cumplir todas tus pantallas. Esto nos asegura que todas tengan una Ruta (para navegar) y un Título (para la barra superior).

Crea el archivo `NavigationDestination.kt`:

```kotlin
interface NavigationDestination {
    /**
     * String único que define la ruta (ej: "lista_recetas")
     * Es la dirección interna que usa Android para saber dónde ir.
     */
    val route: String

    /**
     * ID del recurso de texto que se mostrará en la TopAppBar (ej: R.string.app_name). ¡Ojo en el detalle del plato mostramos el nombre del plato que no es un texto estático! 
     * Por simplicidad se puede manejar en la lógica del TopAppBar --> Si estoy en home screen uso titleRes (R.string...) else (detalle) leo título del ViewModel. Es un apaño en estas apps sencillas para no pasar parámetros, aunque esto último sería lo suyo.
     */
    val titleRes: Int
}
```

-----

### 🍳 Paso 2: Los Objetos de Destino

Crea un objeto (Singleton) para cada pantalla de tu app. En Kotlin podemos tener todos estos objetos en el mismo file .kt, ventajas de Kotlin. Esto evita escribir strings a mano ("strings mágicas") y cometer errores tipográficos.

Crea el archivo `AppDestinations.kt` o añádelo en cada una de los archivos de las screens:

```kotlin
// Destino 1: La pantalla principal
object RecipeListDestination : NavigationDestination {
    override val route = "recipe_list"
    override val titleRes = R.string.app_name
}

// Destino 2: La pantalla secundaria
object RecipeDetailDestination : NavigationDestination {
    override val route = "recipe_detail"
    override val titleRes = R.string.detail_title // Ej. "Detalle del Plato". Si fuera dinámico, lo podríamos leer del ViewModel.
}
```

Nota interna de mejora. Lo de la interfaz es un poco cutre habiendo sealed class, es como lo hacían los codelabs y por no meter un cambio a estas alturas. Pero si alguien lo usa es tal que así. No es preciso, podemos ceñirnos al modo clásico. ¿Ventajas del sealed class? En el when no hay que meter un else artificial para empezar.

```kotlin
sealed class AppDestination(val route: String, val titleRes: Int) {
    data object RecipeList : AppDestination("recipe_list", R.string.recipe_list_title)
    data object RecipeDetail : AppDestination("recipe_detail", R.string.recipe_detail_title)
}
```


-----

### 🍳 Paso 3: El `NavHost` (El Mapa de Carreteras)

Hasta aquí el andamiaje, ahora empieza la implementación real de la navegación. En Navigation 2.X este es el componente más importante. Es el que decide qué pantalla pintar según la ruta actual.

Crea el archivo `MyRecipesNavHost.kt` o `MyRecipesNavGraph.kt` si quieres llamarlo como en los codelabs.

**Puntos Clave:**

* Recibe el `navController` para poder moverse.
* Recibe el `viewModel` **compartido** para pasar datos de una pantalla a otra sin complicaciones. Esto es una *guarrería* que destroza el `preview` entre otras tantas cosas, en el examen es aceptable, fuera de ahí no lo hagas. El viewModel ya tiene lo que necesita cada pantalla, el NavHost puede extraerlo y pasar sólo lo necesario.

```kotlin
@Composable
fun MyRecipesNavHost(
    navController: NavHostController,
    viewModel: RecipeViewModel, // ViewModel compartido (tiene el estado de toda la app)
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RecipeListDestination.route, // 1. ¿Dónde empieza la app?
        modifier = modifier
    ) {
        // --- PANTALLA 1: LISTA ---
        composable(route = RecipeListDestination.route) {
            RecipeListScreen(
                // Pasamos el ViewModel o el estado necesario
                viewModel = viewModel,
                // Evento de navegación: Al hacer click en una receta...
                onRecipeClick = { recetaSeleccionada ->
                    // 1. Guardamos la selección en el ViewModel (Shared State)
                    viewModel.onRecipeSelected(recetaSeleccionada)

                    // 2. Navegamos al detalle (sin pasar argumentos complejos, el VM ya sabe cuál es)
                    navController.navigate(RecipeDetailDestination.route)
                }
            )
        }

        // --- PANTALLA 2: DETALLE ---
        composable(route = RecipeDetailDestination.route) {
            RecipeDetailScreen(
                // Al usar el MISMO viewModel, esta pantalla ya sabe qué receta se seleccionó
                viewModel = viewModel,
                // Evento para volver atrás
                onBack = { navController.popBackStack() }
            )
        }
    }
}
```

Nota Arquitectónica (Future Self). Esto es **ajeno al examen** pero por si se consulta fuera de ese contexto.

**Escenario: ViewModels dedicados por pantalla (sin grafos anidados):**

- Instanciación: El ViewModel NO va en la firma del NavHost. Se instancia (viewModel()) dentro de cada composable individualmente.
- Consecuencia (Estado NO compartido): Al ser instancias distintas, la memoria se pierde al navegar.
- Solución: Es obligatorio pasar la información mediante argumentos de navegación (IDs en la ruta u objetos @Serializable).

Alternativa Híbrida: Usar un único SharedViewModel para toda la app, pero exponiendo UiStates independientes (val listUiState, val detailUiState) para mantener el orden sin perder la memoria compartida.

Grafos Anidados (Nested Navigation): Consiste en agrupar pantallas relacionadas (ej: Login, Checkout) en sub-grafos dentro de la navegación principal.

-----

### 🍳 Paso 4: Integración en el `Scaffold` (`MyRecipesApp`)

Finalmente, conecta todo en tu archivo principal de UI (donde está el `Scaffold`).

**Objetivo:** Que la barra superior cambie de título automáticamente y el botón de "Atrás" aparezca cuando toque.

```kotlin
@Composable
fun MyRecipesApp(
    viewModel: RecipeViewModel = viewModel(), // Inyectamos/Creamos el VM aquí
    navController: NavHostController = rememberNavController() // Creamos el Controller
) {
        // Trucazo 🤙: Observamos la ruta actual para cambiar el título de la TopBar
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Lógica para decidir qué título mostrar según la ruta donde estemos
    val currentScreenTitle = when (currentRoute) {
        RecipeListDestination.route -> stringResource(RecipeListDestination.titleRes)
        RecipeDetailDestination.route -> stringResource(RecipeDetailDestination.titleRes)
        else -> stringResource(R.string.app_name)
    }

    // Lógica para saber si podemos volver atrás (para mostrar la flechita)
    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            MyRecipesTopAppBar(
                title = currentScreenTitle,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        // Aquí llamamos a nuestro NavHost personalizado
        MyRecipesNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
    ...
    
    //Y aquí la definición de nuestro TopAppBar personalizado, que será casi siempre la misma

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyRecipesTopAppBar(
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, //Ten en cuenta que según la versión del ComposeBom puede que Icons no venga importado.
                            contentDescription = stringResource(R.string.back_button) 
                        )
                    }
                }
            }
        )
    }

}
```

### 💡 Resumen del Flujo Lógico

1. **Interface:** Define las reglas del juego.
2. **Objects:** Definen las rutas fijas (para no equivocarnos al escribir).
3. **NavHost:** Define **qué** pantalla se pinta para cada ruta y **cómo** se pasa de una a otra.
4. **App:** Es el contenedor. Crea el `NavController`, observa dónde estamos para pintar la TopBar correcta y aloja al `NavHost`.

## Serializable Edition

Esta es tu receta de cocina para implementar navegación moderna (**Type-Safe**) aunque no tan moderna como **Navigation 3.0**, eso lo dejamos para el futuro, "los experimentos con gaseosa" como diría Eugine D'Orts. Si lo decides hacer con String-Routes pues también me va a servir, no voy a penalizarlo, pero por aprovechar la ocasión ya que prácticamente es lo mismo.

### 🥘 Ingredientes Previos (Serializable edition)

Además de los ingredientes previos de la edición clásica:

1. **Dependencias:** Revisa que en tu `build.gradle.kts` (Module: app) tienes la librería de navegación (normalmente ya viene en el proyecto base):
   `implementation("androidx.navigation:navigation-compose:2.9.X")`
2. **Orden:** Crea un paquete llamado `ui/navigation` para no mezclar ficheros.

Deberemos añadir algún otro import:

En la sección de `plugins` del `build.gradle.kts` (Module: app):

```kotlin
    alias(libs.plugins.kotlin.serialization)
```

A continuación, saltamos a `libs.versions.toml` y añadimos la dependencia de Kotlinx Serialization, en la sección de `[plugins]`:

```kotlin
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
```

Si estás leyendo esto en el examen, puedes estar tranquilo, en general ya lo tendrás todo preparado en el proyecto base.

-----

### 🍳 Paso 1: Los Objetos de Destino (Tus Pantallas)

Aquí está el primer gran cambio. Ya no definimos rutas como textos `"home"`. Ahora definimos las pantallas como objetos serializables.

Crea el archivo `AppDestinations.kt`. Al usar `@Serializable`, Kotlin crea la ruta por ti automáticamente.

```kotlin
// Pantalla 1: Lista de Recetas
@Serializable
object RecipeListDestination

// Pantalla 2: Detalle de Receta
@Serializable
object RecipeDetailDestination

// Nota: Ya no necesitamos la interfaz con 'val route' porque el objeto ES la ruta.

```

### 🍳 Paso 2: El NavHost (El Mapa de Carreteras)

El NavHost ahora es "inteligente" (Genérico). En lugar de pasarle un string, le pasamos el tipo de clase `<T>`.

Archivo `MyRecipesNavHost.kt`:

```kotlin
@Composable
fun MyRecipesNavHost(
    navController: NavHostController,
    viewModel: RecipeViewModel, // ViewModel compartido
    modifier: Modifier = Modifier
) {
    // 1. startDestination ahora recibe el OBJETO, no un string
    NavHost(
        navController = navController,
        startDestination = RecipeListDestination, 
        modifier = modifier
    ) {
        // --- PANTALLA 1: LISTA ---
        // Fíjate en los diamantes < >. Eso es la navegación serializable.
        composable<RecipeListDestination> {
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = { receta ->
                    viewModel.onRecipeSelected(receta) // Guardamos estado en VM
                    
                    // 2. Navegamos pasando el OBJETO destino
                    navController.navigate(RecipeDetailDestination)
                }
            )
        }

        // --- PANTALLA 2: DETALLE ---
        composable<RecipeDetailDestination> {
            RecipeDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

```

### 🍳 Paso 3: Integración en el Scaffold (MyRecipesApp)

Aquí conectamos la barra superior.
**El reto:** Antes comparábamos Strings (`if route == "home"`).
**La solución:** Ahora preguntamos a la ruta si pertenece a una clase (`hasRoute<Clase>()`).

Archivo `MyRecipesApp.kt`:

```kotlin
@Composable
fun MyRecipesApp(
    viewModel: RecipeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // 1. Observamos dónde estamos
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    // 2. Lógica para el Título (Usando hasRoute<T>)
    // Esto es mucho más seguro que comparar strings
    val currentScreenTitle = when {
        currentDestination?.hasRoute<RecipeListDestination>() == true -> stringResource(R.string.app_name)
        currentDestination?.hasRoute<RecipeDetailDestination>() == true -> stringResource(R.string.detail_title)  //De nuevo si fuera dinámico, lo leeríamos del VM
        else -> stringResource(R.string.app_name)
    }

    // 3. Lógica para la flecha de atrás
    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            MyRecipesTopAppBar(
                title = currentScreenTitle,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        MyRecipesNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

```

---

### 💡 Resumen de Cambios (Cheat Sheet)

| Concepto | Antes (Legacy) | **Ahora (Serializable)** |
| --- | --- | --- |
| **Definición** | `object Home { val route = "home" }` | `@Serializable object Home` |
| **NavHost** | `composable("home") { ... }` | `composable<Home> { ... }` |
| **Navegar** | `navController.navigate("home")` | `navController.navigate(Home)` |
| **Verificar Ruta** | `if (route == "home")` | `if (dest.hasRoute<Home>())` |

---

### 🍳 Bonus Track: Pasando Argumentos (El ingrediente secreto)

¿Qué pasa si no usamos un ViewModel compartido y queremos pasar el `id` de la receta (o un `order_id`) a la siguiente pantalla para que ella misma cargue los datos?

¡Esto no se pide en el examen! Pero es un extra interesante para que veas lo sencillo que es con navegación serializable. En la edición clásica esto es un dolor de cabeza (strings, slashes, parsing...).

Aquí es donde la navegación serializable brilla.

#### 1. El Destino cambia de `Object` a `Data Class`

Si la pantalla espera recibir datos, ya no es un objeto estático, es una clase de datos.

```kotlin
@Serializable
// En lugar de 'object', usamos 'data class' con los argumentos que necesitamos
data class RecipeDetailDestination(
    val recipeId: Int, // El famoso ID
    val origin: String // Podemos pasar más cosas si queremos
)

```

#### 2. Al Navegar (El envío)

Instanciamos la clase como si fuera cualquier otra clase de Kotlin.

```kotlin
// En la Screen A (Lista)
onRecipeClick = { idReceta ->
    // ¡Así sí! Sin slashes / ni strings concatenados
    navController.navigate(RecipeDetailDestination(recipeId = idReceta, origin = "Home"))
}

```

#### 3. En el NavHost (La recepción)

Aquí usamos la función mágica `.toRoute<T>()`. Esta función lee la ruta interna, la deserializa y te devuelve tu objeto `data class` relleno.

**¡OJO!** Necesitas el `backStackEntry` (la lambda del composable).

```kotlin
composable<RecipeDetailDestination> { backStackEntry ->
    
    // Recuperamos el objeto con sus datos tipados
    val args = backStackEntry.toRoute<RecipeDetailDestination>()
    
    // Ya tenemos el ID limpio y seguro (es un Int, no un String)
    val id = args.recipeId 
    
    RecipeDetailScreen(
        idToLoad = id, // Se lo pasamos a la pantalla
        viewModel = viewModel, // Si usas VM, la pantalla usará el ID para pedir datos al VM
        onBack = { navController.popBackStack() }
    )
}

```