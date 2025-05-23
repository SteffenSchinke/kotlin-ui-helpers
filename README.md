
# Kotlin UI Helper (Eng)

A lightweight UI helper module for Android projects using **Jetpack Compose**.

This module provides basic structures to efficiently organize **navigation**, **app launch**, and **modular screen design** in Compose projects.

---

## Features

- **AppLauncher** – Initial loading screen & control of the launch process  
- **AppNavigator** – Navigation with support for screens, sheets & tabs  
- **AppRoute / AppTabRoute / AppScreenContent** – Interfaces for a modular UI architecture  
- Focus on **reusability**, **clean structure**, and **scalability**

---

## Installation

### 1. Add as a Git submodule

```bash
git submodule add https://github.com/SteffenSchinke/kotlin-ui-helpers.git
git submodule init
git submodule update
```

Or clone directly:
```bash
git clone https://github.com/SteffenSchinke/kotlin-ui-helpers.git
```

### 2. Integrate into your project

In your root project's `build.gradle.kts`:
```bash
dependencies {
    implementation(project(":kotlin-ui-helpers"))
}
```

In your `settings.gradle.kts`:
```bash
include(":kotlin-ui-helpers")
```

## Architecture Overview

**AppRoute**  
Base interface for all screen routes.
```bash
interface AppRoute {
    val route: String
    val arguments: List<NamedNavArgument> get() = emptyList()
}
```

**AppTabRoute**  
Extension of AppRoute for tab navigation.
```bash
interface AppRouteTab: AppRoute {
    @get:Composable
    val tabTitle: String
    val tabIcon: @Composable () -> Unit
}
```

**AppRouteContent**  
Extension to render any previous route type in the UI Screen.
```bash
interface AppRouteContent: AppRoute {

    val viewModelDependencies: Map<KClass<out ViewModel>, @Composable () -> ViewModel>

    @OptIn(ExperimentalMaterial3Api::class)
    val content: (@Composable (
        viewModels: Map<KClass<out ViewModel>, ViewModel>,
        navController: NavHostController,
        sheetState: SheetState,
        args: Bundle?,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit,
        onDismiss: () -> Unit) -> Unit)?

    val topBar: (@Composable (
        viewModels: Map<KClass<out ViewModel>, ViewModel>,
        navController: NavHostController,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit
    ) -> Unit)?

    val fab: (@Composable (
        viewModels: Map<KClass<out ViewModel>, ViewModel>,
        navController: NavHostController,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit
    ) -> Unit)?
}
```

**AppRouteSheet**  
Extension to render any previous route type in the UI Sheet.
```bash
interface AppRouteSheet  {

    val viewModelDependencies: Map<KClass<out ViewModel>, @Composable () -> ViewModel>

    @OptIn(ExperimentalMaterial3Api::class)
    val contentSheet: @Composable (
        viewModels: Map<KClass<out ViewModel>, ViewModel>,
        navController: NavHostController,
        sheetState: SheetState,
        args: Bundle?,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit,
        onDismiss: () -> Unit
    ) -> Unit
}

```

## Architecture Principle

**Combined implementation with tab navigation:**
```bash
object HomeRoute: AppTabRoute, AppRouteContent {

    override val title: String = "Home"
    override val route = "home"
    override val tabIcon: @Composable () -> Unit
        get() = {
            Icon(Icons.Default.Home, "Home")
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override val content: @Composable ((Map<KClass<out ViewModel>, ViewModel>,
                                        NavHostController,
                                        SheetState,
                                        Bundle?,
                                        (AppRouteSheet, Bundle?) -> Unit,
                                        () -> Unit) -> Unit)?
        get() = { _, _, _, _, _, _, ->
           Column {
               Text("Home content")
               Button(onClick = { navController.navigate(DetailRoute.route) }) {
                   Text("Go details")
               }
           }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    override val topBar: @Composable ((Map<KClass<out ViewModel>, ViewModel>,
                                        NavHostController,
                                        (AppRouteSheet, Bundle?) -> Unit) -> Unit)?
        get() =  { _, _, _, ->
            return {
                TopAppBar(
                    title = {
                        Text("Home")
                    }
                )
            }
        }
}
```

**Combined implementation as sub-composable without sheet navigation:**
```bash
object TaskAdd: AppRouteContent, AppRouteSheet {

    override val route: String
        get() = "task_add"

    @OptIn(ExperimentalMaterial3Api::class)
    override val contentSheet: @Composable (navController: NavHostController,
                                             sheetState: SheetState,
                                             args: Bundle?,
                                             onShowSheet: (AppRouteSheet, Bundle?) -> Unit,
                                             onDismiss: () -> Unit) -> Unit
        get() = { _, _, _, _, _ ->
            Add(..., ..., onDismiss)
        }

    @OptIn(ExperimentalMaterial3Api::class)
    override val content: @Composable ((NavHostController, SheetState, Bundle?,
                                        (AppRouteSheet, Bundle?) -> Unit, () -> Unit) -> Unit)?
        get() = null

    override val topBar: @Composable ((NavHostController, (AppRouteSheet, Bundle?) -> Unit) -> Unit)?
        get() = null

    override val fab: @Composable ((NavHostController, (AppRouteSheet, Bundle?) -> Unit) -> Unit)?
        get() = null
}
```

## Author

**Steffen Schinke**  
📧 steffen.schinke@gmail.com  
🔗 [GitHub](https://github.com/SteffenSchinke)

### Skills

- **Languages & Frameworks**:  
  C++, C#, VB.NET, Swift 6, Kotlin 2 
  HTML 5, CSS 3, JavaScript, PHP 8

- **Databases**:  
  MySQL, Oracle, SQL Server


---
---
