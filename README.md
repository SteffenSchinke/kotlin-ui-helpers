
# Kotlin UI Helper (Eng)

A lightweight UI helper module for Android projects using **Jetpack Compose**.

This module provides basic structures to efficiently organize **navigation**, **app launch**, and **modular screen design** in Compose projects.

---

## Features

- **AppLauncher** – Initial loading screen & control of the launch process  
- **AppNavigator** – Navigation with support for screens, sheets & tabs and integrated snackbar system
- **AppRoute / AppRouteTab / AppRouteContent / AppRouteSheet** – Interfaces for a modular UI architecture  
- **AppBaseViewModel || AppBaseViewModelAndroid** Inhered from this for correctly life circles
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

    // only description from dependencies view models
    // instance build in the AppNavigator and return as map of instances with correctly life circle
    override val viewModelDependencies: Map<KClass<out ViewModel>, @Composable (() -> ViewModel)>
        get() = mapOf(
            HomeViewModel::class to { koinViewModel<HomeViewModel>() }
        )

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override val content: @Composable ((Map<KClass<out ViewModel>, ViewModel>,
                                        NavHostController,
                                        SheetState,
                                        Bundle?,
                                        (AppRouteSheet, Bundle?) -> Unit,
                                        () -> Unit) -> Unit)?
        get() = { _, navController, _, _, _, _, ->
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
              TopAppBar(
                  title = {
                      Text("Home")
                  }
              )
          }
}
```

**Combined implementation as sub-composable without sheet navigation:**
```bash
object Details: AppRoute, AppRouteSheet {

    override val route: String
        get() = "details"

    // only description from dependencies view models
    // instance build in the AppNavigator and return as map of instances with correctly life circle
    override val viewModelDependencies: Map<KClass<out ViewModel>, @Composable (() -> ViewModel)>
        get() = mapOf(
            DetailsViewModel::class to { koinViewModel<DetailsViewModel>() }
        )

    @OptIn(ExperimentalMaterial3Api::class)
    override val contentSheet: @Composable (Map<KClass<out ViewModel>, ViewModel>
                                            navController: NavHostController,
                                            sheetState: SheetState,
                                            args: Bundle?,
                                            onShowSheet: (AppRouteSheet, Bundle?) -> Unit,
                                            onDismiss: () -> Unit) -> Unit
        get() = { _, _, _, _, _, onDismiss ->
            Details([...], [...], onDismiss)
        }
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
