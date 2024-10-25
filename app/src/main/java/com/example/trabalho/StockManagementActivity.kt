package com.example.trabalho

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trabalho.Bd.AppDataBase
import com.example.trabalho.Class.Litragem
import com.example.trabalho.Class.Marca
import com.example.trabalho.Class.Produto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.*

class StockManagementActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            StockView(navController = navController)
        }
    }
}

@Composable
fun StockView(navController: NavController) {

    var productList by remember { mutableStateOf<List<Produto>>(emptyList()) }
    var marcaList by remember { mutableStateOf<List<Marca>>(emptyList()) }
    var litragemList by remember { mutableStateOf<List<Litragem>>(emptyList()) }

    val context = LocalContext.current
    val dbConnection = AppDataBase.getDataBase(context)
    val produtoDAO = dbConnection.produtoDao()
    val marcaDAO = dbConnection.marcaDao()
    val litragemDAO = dbConnection.litragemDao()

    LaunchedEffect(Unit) {
        try {
            productList = produtoDAO.getAllProdutos()
            marcaList = marcaDAO.getAllMarcas()
            litragemList = litragemDAO.getAllLitragens()
        } catch (e: Exception) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BackStackActivity(navController)

            FilterActivity()

        }

        if (productList.isEmpty()) {
            Text(text = "Lista de Produtos Vazia")
        } else {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(productList, key = { it.codigo }) { produto ->
                    val marca = marcaList.find { it.id == produto.marcaId }
                    val litragem = litragemList.find { it.id == produto.litragemId }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = marca?.marca ?: "Marca desconhecida",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = produto.produto,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = litragem?.litragem ?: "Litragem desconhecida",
                                    fontSize = 18.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Quantidade: ${produto.quantidade}",
                                    fontSize = 18.sp,
                                )

                                Button(
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            try {
                                                val updatedProduto = produto.copy(quantidade = produto.quantidade + 1)
                                                produtoDAO.updateProduto(updatedProduto)
                                                    withContext(Dispatchers.Main) {
                                                        productList = productList.map {
                                                            if (it.codigo == produto.codigo) updatedProduto else it
                                                        }
                                                }
                                            } catch (e: Exception) {
                                                Log.e("Error ao Aumentar a quantidade", "${e.message}")
                                            }
                                        }
                                    },
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(text = "+", color = Color.White)
                                }

                                Button(
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            try {
                                                val updatedProduto = produto.copy(quantidade = produto.quantidade - 1)
                                                produtoDAO.updateProduto(updatedProduto)
                                                    withContext(Dispatchers.Main) {
                                                        productList = productList.map {
                                                            if (it.codigo == produto.codigo) updatedProduto else it
                                                        }
                                                }
                                            } catch (e: Exception) {
                                                Log.e("Error ao Diminuir a quantidade", "${e.message}")
                                            }
                                        }
                                    },
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(text = "-", color = Color.White)
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}
