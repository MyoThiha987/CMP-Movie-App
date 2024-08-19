package com.myothiha.compose.movie.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myothiha.compose.movie.ui.navigation.AppGraph
import com.myothiha.compose.movie.ui.navigation.AppScreens
import moviecomposemultiplatform.composeapp.generated.resources.Res
import moviecomposemultiplatform.composeapp.generated.resources.ic_favourite
import moviecomposemultiplatform.composeapp.generated.resources.ic_home
import moviecomposemultiplatform.composeapp.generated.resources.lbl_bookmark
import moviecomposemultiplatform.composeapp.generated.resources.lbl_home
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * @Author Liam
 * Created at 18/Aug/2024
 */

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val bottomNavList = listOf(
        BottomNavigationItem(
            label = Res.string.lbl_home,
            icon = painterResource(resource = Res.drawable.ic_home),
            route = AppScreens.HomeScreen.route
        ),
        BottomNavigationItem(
            label = Res.string.lbl_bookmark,
            icon = painterResource(resource = Res.drawable.ic_favourite),
            route = AppScreens.FavouriteMovieScreen.route
        )

    )
    val nav = remember {
        mutableStateOf(bottomNavList)
    }

    AnimatedVisibility(
        visible = shouldShowBottomBar(
            currentDestination = currentDestination,
            navItemList = nav
        ),
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = 500,
            ),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = 500,
            ),
            targetOffsetY = { it }
        ),
    ) {
        NavigationBar(
            navItemList = nav,
            navDestination = currentDestination,
            navController = navController
        )
    }

}

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    navItemList: androidx.compose.runtime.State<List<BottomNavigationItem>>,
    navDestination: NavDestination?,
    navController: NavController
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))

        androidx.compose.material3.NavigationBar(
            containerColor = Color.White,
            modifier = modifier
        ) {
            navItemList.value.forEach { destination ->
                BottomNavigationBarItem(
                    screen = destination,
                    currentDestination = navDestination,
                    navController = navController
                )
            }

        }
    }
}

@Composable
fun RowScope.BottomNavigationBarItem(
    screen: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    NavigationBarItem(
        label = {
            Text(
                text = stringResource(resource = screen.label),
                style = MaterialTheme.typography.labelMedium,
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        enabled = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } != true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().route ?: AppGraph.HOME) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = {
            Icon(
                painter = screen.icon,
                contentDescription = "Bottom Navigation Icon"
            )
        },
        colors = NavigationBarItemColors(
            selectedIconColor = Color.Black,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            selectedIndicatorColor = Color.Gray.copy(alpha = 0.2f),
            selectedTextColor = Color.Black,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            disabledIconColor = Color.Black,
            disabledTextColor = Color.Black,
        )
    )
}

private fun shouldShowBottomBar(
    currentDestination: NavDestination?,
    navItemList: androidx.compose.runtime.State<List<BottomNavigationItem>>,
): Boolean {
    return navItemList.value.any {
        it.route == currentDestination?.route
    }
}


data class BottomNavigationItem(
    val label: StringResource,
    val icon: Painter,
    val route: String
)