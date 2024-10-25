package com.example.trabalho

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavController() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "Menu") {
        composable("Menu") { AppMenu(navController) }
        composable("Stock") { StockView(navController) }
        composable("NewProduct") { NewProductView(navController) }
        composable("NewMarca") { NewMarcaView(navController) }
        composable("NewLitragem") { NewLitragemView(navController) }
        //composable("ProductInfo") { ProductInfoView(navController, codigo) }
    }

}