package com.xy.composedemo

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@ExperimentalMaterialApi
@Composable
fun ComposePages() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "first_page") {

        composable("first_page") { MainContent(navController) }
        composable("second_page") { RefreshDefaultHeaderSimple() }
        composable("third_page") { RefreshLottietHeaderSimple() }
        composable("fourth_page") { RefreshAndLoadMoreSimple() }
    }
}