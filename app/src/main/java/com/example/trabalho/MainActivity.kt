package com.example.trabalho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalho.ui.theme.TrabalhoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavController()
        }
    }
}

@Composable
fun AppMenu(navController: NavController) {

    Column(

        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Button(onClick = {navController.navigate("Stock")}) {
            Text(text = "Estoque")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {navController.navigate("NewProduct")}) {
            Text(text = "Cadastrar Novo Produto")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {navController.navigate("NewMarca")}) {
            Text(text = "Cadastrar Nova Marca ")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {navController.navigate("NewLitragem")}) {
            Text(text = "Cadastrar Nova Litragem")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun AppMenuPreview() {
    val navController = rememberNavController()
    AppMenu(navController = navController)
}