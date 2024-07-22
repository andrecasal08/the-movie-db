package com.acdevs.themoviedb.utilities

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

var selectedItemIndex = mutableIntStateOf(0).value

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            route = "home_screen",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Favorites",
            route = "favorites_screen",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            hasNews = false
        )
    )

    NavigationBar(
        contentColor = Color.White, // tap background color
        containerColor =  Color.White, // tap background color
        modifier = modifier.height(100.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor =  Color.White,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    selectedIconColor =  textTitleColor,
                    unselectedIconColor =  textTitleColor
                ),
                alwaysShowLabel = false,
                //label = { Text(text = item.title, color = Color.White) },
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(

                        imageVector = if (selectedItemIndex == index) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        }, contentDescription = item.title
                    )
                })
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)