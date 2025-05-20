package de.schinke.steffen.interfaces

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlin.reflect.KClass

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
