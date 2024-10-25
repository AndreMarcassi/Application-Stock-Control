package com.example.trabalho

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalho.Bd.AppDataBase
import com.example.trabalho.Bd.LitragemDao
import com.example.trabalho.Bd.MarcaDao
import com.example.trabalho.Class.Litragem
import com.example.trabalho.Class.Marca
import com.example.trabalho.ui.theme.TrabalhoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewLitragemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun NewLitragemView(navController: NavController) {

    var litragem by remember { mutableStateOf("") }

    val context = LocalContext.current
    val dbConnection = AppDataBase.getDataBase(context)
    val litragemDAO = dbConnection.litragemDao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cadastrar Nova Litragem",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        TextField(
            value = litragem,
            onValueChange = { litragem = it },
            label = { Text(text = "Marca:") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                if (litragem.isNotBlank()) {
                    Cadastrar(litragemDAO,litragem,navController)
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

fun Cadastrar(litragemDao: LitragemDao, litragem: String, navController: NavController) {
    try {
        CoroutineScope(Dispatchers.IO).launch {

            val litragem = Litragem(0, litragem)
            litragemDao.AddLitragem(litragem)

            withContext(Dispatchers.Main) {
                navController.navigate("Menu")
            }

        }
    } catch (e: Exception) {
        Log.e("Error ao Cadastrar", "${e.message}")
    }
}