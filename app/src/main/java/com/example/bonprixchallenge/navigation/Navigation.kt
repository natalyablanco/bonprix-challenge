package com.example.bonprixchallenge.navigation

import androidx.navigation.NavController

class Navigation {

    sealed class Screen(val route: String) {
        object Home : Screen("home")
        object Children : Screen("children")
    }


    class NavActions(private val navigator: ScreenNavigator) {
        fun goToHome() = navigator.navigate(Screen.Home)
        fun goToChildren() = navigator.navigate(Screen.Children)
        fun pop() = navigator.pop()
    }

    class ScreenNavigator(private val navController: NavController) {
        fun navigate(screen: Screen) = navController.navigate(screen.route)
        fun pop() = navController.popBackStack()
    }
}