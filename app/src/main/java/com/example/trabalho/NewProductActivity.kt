package com.example.trabalho

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalho.Bd.AppDataBase
import com.example.trabalho.Bd.LitragemDao
import com.example.trabalho.Bd.MarcaDao
import com.example.trabalho.Bd.ProdutoDao
import com.example.trabalho.Class.Litragem
import com.example.trabalho.Class.Marca
import com.example.trabalho.Class.Produto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun NewProductView(navController: NavController) {

    var codigo by remember { mutableStateOf("") }
    var produto by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }


    var litragem by remember { mutableStateOf("") }
    var litragemExpanded by remember { mutableStateOf(false) }
    var litragems by remember { mutableStateOf(listOf<String>()) }

    var marca by remember { mutableStateOf("") }
    var marcaExpanded by remember { mutableStateOf(false) }
    var marcas by remember { mutableStateOf(listOf<String>()) }


    val context = LocalContext.current
    val dbConnection = AppDataBase.getDataBase(context)
    val produtoDAO = dbConnection.produtoDao()
    val marcaDAO = dbConnection.marcaDao()
    val litragemDAO = dbConnection.litragemDao()

    LaunchedEffect(Unit) {
        marcas = marcaDAO.getAllMarcas().map { it.marca }
        litragems = litragemDAO.getAllLitragens().map { it.litragem }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cadastrar Novo Produto",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        TextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text(text = "Codigo do Produto:") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Box {
            TextField(
                value = marca,
                onValueChange = { },
                label = { Text(text = "Marca do Produto:") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Marca",
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            marcaExpanded = !marcaExpanded
                        }
                    )
                }
            )

            DropdownMenu(
                expanded = marcaExpanded,
                onDismissRequest = { marcaExpanded = false }
            ) {
                marcas.forEach { marcaItem ->
                    DropdownMenuItem(
                        text = {Text(text = marcaItem)},
                        onClick = {
                            marca = marcaItem
                            marcaExpanded = false
                        }
                    )
                }
            }
        }

        TextField(
            value = produto,
            onValueChange = { produto = it },
            label = { Text(text = "Nome do Produto:") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Box {
            TextField(
                value = litragem,
                onValueChange = { },
                label = { Text(text = "Litragem do Produto:") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Litragem",
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            litragemExpanded = !litragemExpanded
                        }
                    )
                }
            )

            DropdownMenu(
                expanded = litragemExpanded,
                onDismissRequest = { litragemExpanded = false }
            ) {
                litragems.forEach { litragemItem ->
                    DropdownMenuItem(
                        text = { Text(text = litragemItem) },
                        onClick = {
                            litragem = litragemItem
                            litragemExpanded = false
                        }
                    )
                }
            }
        }

        TextField(
            value = quantidade,
            onValueChange = { quantidade = it },
            label = { Text(text = "Quantidade do Produto:") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (codigo.isNotBlank() && marca.isNotBlank() && produto.isNotBlank() && litragem.isNotBlank() && quantidade.isNotBlank()) {
                    cadastrar(
                        produtoDAO, marcaDAO, litragemDAO,
                        codigo.toInt(),
                        marca,
                        produto,
                        litragem,
                        quantidade.toInt(),
                        navController
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                text = "Cadastrar",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

fun cadastrar(
    produtoDAO: ProdutoDao,
    marcaDAO: MarcaDao,
    litragemDAO: LitragemDao,
    codigo: Int,
    marca: String,
    nome: String,
    litragem: String,
    quantidade: Int,
    navController: NavController
) {
    try {
        CoroutineScope(Dispatchers.IO).launch {

            val marcaId = marcaDAO.getMarcaIdByName(marca) ?: run {
                val novaMarca = Marca(id = 0, marca = marca)
                marcaDAO.AddMarca(novaMarca)
                marcaDAO.getMarcaIdByName(marca)
            }

            val litragemId = litragemDAO.getLitragemIdByValue(litragem) ?: run {
                val novaLitragem = Litragem(id = 0, litragem = litragem)
                litragemDAO.AddLitragem(novaLitragem)
                litragemDAO.getLitragemIdByValue(litragem)
            }

            val produto = Produto(
                codigo = codigo,
                marcaId = marcaId!!,
                produto = nome,
                quantidade = quantidade,
                litragemId = litragemId!!
            )

            produtoDAO.AddProduto(produto)

            withContext(Dispatchers.Main) {
                navController.navigate("Menu")
            }
        }
    } catch (e: Exception) {
        Log.e("Error ao Cadastrar", "${e.message}")
    }
}