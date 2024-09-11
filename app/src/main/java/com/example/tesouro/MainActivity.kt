package com.example.tesouro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tesouro.ui.theme.TesouroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesouroTheme {
                val navigationController = rememberNavController()
                NavHost(navController = navigationController, startDestination = "home") {
                    composable("home") { HomeScreen(navigationController) }
                    composable("pista1") { Pista1Screen(navigationController) }
                    composable("pista2") { Pista2Screen(navigationController) }
                    composable("pista3") { Pista3Screen(navigationController) }
                    composable("treasure") { TreasureScreen(navigationController) }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bem-vindo à caça ao tesouro!", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("pista1") }) {
            Text(text = "Iniciar caça ao tesouro")
        }
    }
}

@Composable
fun Pista1Screen(navController: NavController) {
    PistaScreen(
        navController = navController,
        pista = "Pista 1: O que é que sempre cai em pé e corre deitado?",
        respostaCorreta = "chuva",
        proximaTela = "pista2"
    )
}

@Composable
fun Pista2Screen(navController: NavController) {
    PistaScreen(
        navController = navController,
        pista = "Pista 2: O que é que tem pescoço, mas não tem cabeça?",
        respostaCorreta = "garrafa",
        proximaTela = "pista3"
    )
}

@Composable
fun Pista3Screen(navController: NavController) {
    PistaScreen(
        navController = navController,
        pista = "Pista 3: O que é que você quebra ao falar?",
        respostaCorreta = "silencio",
        proximaTela = "treasure"
    )
}

@Composable
fun PistaScreen(
    navController: NavController,
    pista: String,
    respostaCorreta: String,
    proximaTela: String
) {
    var resposta by remember { mutableStateOf("") }
    var respostaErrada by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = pista, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = resposta,
            onValueChange = { resposta = it },
            label = { Text(text = "Digite a resposta") }
        )

        if (respostaErrada) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Resposta incorreta, tente novamente.", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (resposta.equals(respostaCorreta, ignoreCase = true)) {
                navController.navigate(proximaTela)
            } else {
                respostaErrada = true
            }
        }) {
            Text(text = "Próxima pista")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Voltar")
        }
    }
}

@Composable
fun TreasureScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Parabéns! Você encontrou o tesouro!", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Voltar ao início")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TesouroTheme {
        HomeScreen(rememberNavController())
    }
}