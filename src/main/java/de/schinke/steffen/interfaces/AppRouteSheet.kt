package de.schinke.steffen.interfaces

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface AppRouteSheet  {

    @OptIn(ExperimentalMaterial3Api::class)
    val contentSheet: @Composable (
        navController: NavHostController,
        sheetState: SheetState,
        args: Bundle?,
        onShowSheet: (AppRouteSheet, Bundle?) -> Unit,
        onDismiss: () -> Unit
    ) -> Unit
}
