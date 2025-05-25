package de.schinke.steffen.ui.components


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import de.schinke.steffen.ui.R

@Composable
fun CostumBackButton(

    navController: NavHostController,
    tintColor: Color = MaterialTheme.colorScheme.onSurface
) {

    IconButton(
        onClick = { navController.popBackStack() },
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = tintColor
        ),
        content = { Icon(painterResource(R.drawable.ic_arrow_back), "Back") }
    )
}