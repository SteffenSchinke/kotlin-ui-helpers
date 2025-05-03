
# Kotlin UI Helper

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
git submodule add https://github.com/SteffenSchinke/kotlin-ui-helper.git
```

Or clone directly:
```bash
git clone https://github.com/SteffenSchinke/kotlin-ui-helper.git
```

### 2. Integrate into your project

In your root project's `build.gradle.kts`:
```bash
dependencies {
    implementation(project(":kotlin-ui-helper"))
}
```

In your `settings.gradle.kts`:
```bash
include(":kotlin-ui-helper")
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
interface AppTabRoute : AppRoute {
    val title: String
    val icon: ImageVector
}
```

**AppScreenContent**  
Extension to render any previous route type in the UI.
```bash
interface AppScreenContent {

    @Composable
    fun Content(innerPadding: PaddingValues, navController: NavHostController): @Composable () -> Unit

    @Composable
    fun TopBar(navController: NavHostController): (@Composable () -> Unit)? = null

    @Composable
    fun SnackBar(): (@Composable () -> Unit)? = null

    @Composable
    fun Fab(navController: NavHostController): (@Composable () -> Unit)? = null
}
```

## Architecture Principle

**Combined implementation with tab navigation:**
```bash
object HomeRoute: AppTabRoute, AppScreenContent {

    override val title: String = "Home"
    override val icon: ImageVector = Icons.Default.Home
    override val route = "home"

    @Composable
    override fun Content(
        padding: PaddingValues,
        navController: NavHostController) {

           Column {
               Text("Home content")

               Button(onClick = { navController.navigate(DetailRoute.route) }) {
                   Text("Go details")
               }
           }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun TopBar(navController: NavHostController): @Composable (() -> Unit)? {

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

**Combined implementation as sub-composable without tab navigation:**
```bash
object DetailRoute: AppRoute, AppScreenContent {

    override val route = "detail"

    @Composable
    override fun Content(
        padding: PaddingValues,
        navController: NavHostController) {

        Text("Details content")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun TopBar(navController: NavHostController): @Composable () -> Unit {

        return {

            TopAppBar(
                title = {
                    Text("Details")
                },
                navigationIcon = {

                    BackButton(navController)
                }
            )
        }
    }
}
```

## Author

**Steffen Schinke**  
📧 steffen.schinke.dev@gmail.com  
🔗 [GitHub](https://github.com/SteffenSchinke)

### Skills

- **Languages & Frameworks**:  
  C++, C#, VB.NET, Swift 6, Kotlin 2 
  HTML 5, CSS 3, JavaScript, PHP 8

- **Databases**:  
  MySQL, Oracle, SQL Server
