# Examen / Recuperación – Kebab Kiosk (Jetpack Compose)

Este proyecto es la base del examen. Partimos de una app Compose con dos pantallas:
- `OrderScreen`: pantalla de pedido (pan, carne, toppings). Actualmente tiene estado y lógica dentro de la UI.
- `SummaryScreen`: pantalla resumen con barra inferior y botón **PAGAR**.

Además, hay esqueletos pendientes:
- `ui/navigation/KebabKioskNavGraph.kt` (TODO)
- `ui/navigation/AppDestination.kt` (TODO)
- `ui/screens/OrderScreenViewModel.kt` (vacío)
- `ui/screens/OrderScreenUiState.kt` (vacío)

> **Importante:** La recuperación es independiente y se evalúa sobre 10. La parte MVVM + Navegación se evalúa como “segundo examen” sobre 10 (5 + 5).

---

## 1) Recuperación (UI / Estilo) — 10 puntos

**Tiempo estimado: 30 minutos**

Objetivo: mejorar el layout de la app y demostrar comprensión de elevación de estado mediante componentes reutilizables.

### Requisitos (obligatorios)

> **Nota importante**: El componente `PrimaryButton` vale **6 puntos de 10** porque es lo que mejor demuestra si entiendes composición, reutilización y elevación de estado. Los ajustes de padding/centrado valen **4 puntos** en total.

1. **Padding global de la app** *(3-5 min)* — **1.5 pts**
   - Añadir un padding consistente a *toda la aplicación* para que se vea "estilizado".
   - Debe hacerse desde un punto común (`KebabKioskApp` / `Scaffold`) para no repetirlo en cada pantalla.
   - **Criterio clave**: evitar código duplicado, aplicar DRY (Don't Repeat Yourself).

2. **Centrar los títulos de sección** *(2-3 min)* — **1.5 pts**
   - Todos los títulos de sección (ej: `SectionTitle("1. Elige tu Pan")`) deben estar centrados horizontalmente.

3. **Centrar el botón "Confirmar Pedido"** *(2-3 min)* — **1 pt**
   - El botón de `OrderScreen` debe quedar centrado y bien colocado (alineación/anchura coherente, spacing apropiado).

4. **Crear componente reutilizable `PrimaryButton`** *(15-20 min)* — **6 pts**
   - Extraer un composable reutilizable para los botones principales de la app.
   - El componente debe:
     - Recibir el **texto** que se mostrará en el botón
     - Recibir la **acción** que se ejecutará al hacer clic (como lambda)
     - Aceptar un **modifier** opcional para flexibilidad
     - Implementar un **estilo consistente** (colores, padding, esquinas redondeadas, etc.)
   - Debe usarse tanto en "Confirmar Pedido" como en "PAGAR".
   - **Criterio clave**: demuestra elevación de estado — el botón NO decide qué hace, recibe la acción como parámetro.

---

## 2) Segundo examen: MVVM + Navegación — 10 puntos (5 + 5)

### Regla de oro (obligatoria)

Los **componentes pequeños y reutilizables** (`BreadCard`, `BreadSelector`, `MeatSelector`, `ToppingsList`, `AllergensBar`, etc.) **nunca** deben recibir un `ViewModel`.

- ✅ Correcto: el componente recibe **datos** y **lambdas**
  - `fun BreadCard(bread: BreadType, selected: Boolean, onClick: () -> Unit)`
- ❌ Incorrecto: el componente recibe el ViewModel
  - `fun BreadCard(viewModel: OrderScreenViewModel)`

> El `ViewModel` solo se permite en pantallas “grandes” (`OrderScreen`, `SummaryScreen`) o en el “cableado” del `NavHost`.

---

## 2A) Parte MVVM — 5 puntos

### Objetivo
Sacar el estado y la lógica fuera de `OrderScreen` para que la UI sea declarativa y el estado sea único y consistente.

### Requisitos mínimos

1. Crear un estado inmutable `OrderScreenUiState`
   - Debe representar lo necesario para dibujar la UI (mínimo: `selectedBread`, `selectedMeat`, `selectedToppings`).
   - El precio total puede ir como campo calculado o como función/propiedad derivada.

2. Implementar `OrderScreenViewModel`
   - Debe exponer el estado con un mecanismo observable (recomendado: `StateFlow`).
   - Debe ofrecer eventos/métodos para:
     - seleccionar pan,
     - seleccionar carne,
     - activar/desactivar toppings,
     - confirmar el pedido (aunque sea placeholder).

3. Refactor de `OrderScreen`
   - La pantalla debe **leer el estado** y **emitir eventos**.
   - No debe contener lógica de negocio (por ejemplo: cálculo de totales o toggles “a mano”) incrustada en la UI.

### Opciones válidas (elige una)

- **Opción A: pantalla inteligente**
  - `OrderScreen(viewModel: OrderScreenViewModel = viewModel())`
  - Dentro de `OrderScreen` se observa el estado y se llaman a los métodos del VM.

- **Opción B: pantalla tonta + cableado en navegación**
  - `OrderScreen(uiState: OrderScreenUiState, onBreadSelected: ..., onConfirm: ...)`
  - El VM se maneja desde el `NavHost` y a la pantalla solo le llegan datos + callbacks.

> Extra points: si aplicas correctamente Option B suele quedar un diseño más limpio y testeable.

---

## 2B) Parte Navegación — 5 puntos

### Objetivo
Implementar navegación entre `OrderScreen` y `SummaryScreen` usando Navigation Compose.

### Requisitos mínimos

1. Implementar el grafo en `ui/navigation/KebabKioskNavGraph.kt`
2. Definir destinos (routes) en `AppDestination.kt` (o equivalente)
3. Navegar al pulsar **Confirmar Pedido**
   - Desde `OrderScreen` debe navegar a `SummaryScreen`.
4. Permitir volver atrás
   - Debe funcionar el back (botón sistema o `navController.popBackStack()`).

### Nota sobre paso de datos
- Puedes compartir estado entre pantallas con un ViewModel compartido (opción sencilla).
- O puedes pasar argumentos por ruta (más avanzado; puede contar como extra).

---

## Entregables

- Proyecto compilable.
- Flujo funcionando:
  - Seleccionar pan/carne/toppings
  - Confirmar pedido → ver resumen → botón pagar visible
  - Volver atrás
- Código legible y bien organizado (paquetes `model`, `data`, `ui`).

---

## Rúbrica (evaluación)

### Recuperación UI (10 puntos)

- **(1.5 pts)** Padding global aplicado y consistente
  - 1.5: padding global bien centralizado (no repetido) y se ve correcto
  - 0.75: padding aplicado pero repetido o inconsistente
  - 0: no se aprecia

- **(1.5 pts)** Títulos centrados
  - 1.5: centrado correcto en todas las secciones
  - 0.75: centrado parcial / algunos títulos
  - 0: no centrado

- **(1 pt)** Botón "Confirmar Pedido" centrado y bien alineado
  - 1: alineación correcta, spacing y tamaño coherentes
  - 0.5: centrado pero con problemas evidentes de diseño
  - 0: sin cambios o mejora mínima

- **(6 pts)** Componente reutilizable `PrimaryButton`
  - 6: componente correctamente implementado, usado en ambos botones (Confirmar + Pagar), demuestra elevación de estado (recibe `onClick` como lambda), estilo consistente y profesional
  - 4-5: componente creado y usado en ambos lugares, pero con deficiencias menores (estilo mejorable o pequeños problemas de implementación)
  - 2-3: componente creado pero solo usado en un lugar, o con problemas significativos de diseño/implementación
  - 1: intento de crear componente pero con errores significativos que afectan la funcionalidad
  - 0: no existe o botón "PAGAR" no integrado en el flujo

### MVVM (5 puntos)

- **(2 pts)** `OrderScreenUiState` completo e inmutable
  - incluye selección + datos necesarios para UI, sin duplicar estado

- **(2 pts)** `OrderScreenViewModel` implementado correctamente
  - expone estado observable (ideal `StateFlow`)
  - eventos claros (select bread/meat, toggle topping, confirm)

- **(1 pt)** UI limpia
  - `OrderScreen` no contiene lógica de negocio ni mutación directa del estado

> Penalización: si pasas el ViewModel a componentes pequeños (BreadCard, MeatSelector…), se puede descontar hasta **-2 puntos** en MVVM por acoplamiento.

### Navegación (5 puntos)

- **(2 pts)** Definición de destinos y grafo de navegación
  - routes claras, sin hardcodear strings “por ahí”

- **(2 pts)** Navegación funcional
  - Confirmar pedido navega a Summary

- **(1 pt)** Back navigation
  - volver atrás funciona correctamente

---

## Extras (opcional, para subir nota dentro del apartado)

- Utilizar string resources
- Meter un Toast al clickar pagar.
- Navegar de vuelta al comprar