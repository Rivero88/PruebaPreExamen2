package com.example.pruebapreexamen2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebapreexamen2.data.DataSource
import com.example.pruebapreexamen2.data.LoteriaTipo
import com.example.pruebapreexamen2.data.LoteriasUiState
import com.example.pruebapreexamen2.data.LoteriasViewModel
import com.example.pruebapreexamen2.ui.theme.PruebaPreExamen2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaPreExamen2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal(loterias = DataSource.loterias, viewModelLoteria = viewModel())
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier, loterias: ArrayList<LoteriaTipo>,
                      viewModelLoteria: LoteriasViewModel) {
    val uiState by viewModelLoteria.uiState.collectAsState()
    Column(modifier){
        Text(text = "Bienvenido a apuestas Ester Rivero Goldero",
            modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(start = 20.dp, top = 50.dp))
        PantallaLoterias(loterias = loterias, viewModelLoteria = viewModelLoteria)
        PantallaTextEditor(viewModelLoteria = viewModelLoteria)
        PantallaBotonApostar()
        PantallaTextos(uiState = uiState)
    }
}

@Composable
fun PantallaLoterias(modifier: Modifier = Modifier, loterias: ArrayList<LoteriaTipo>,
                     viewModelLoteria: LoteriasViewModel){
    Column(modifier.height(200.dp)) {
        LazyHorizontalGrid(rows = GridCells.Fixed(1),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center){
            items(loterias){loteria ->
                Card(
                    modifier
                        .width(275.dp)
                        .padding(8.dp)) {
                    Text(text = "Nombre: ${loteria.nombre}",
                        modifier
                            .background(Color.Yellow)
                            .padding(20.dp)
                            .fillMaxWidth())
                    Text(text = "Premio: ${loteria.premio} €",
                        modifier
                            .background(Color.Cyan)
                            .padding(20.dp)
                            .fillMaxWidth())
                    Button(onClick = { viewModelLoteria.ApostarLoteria(loteria, loterias, viewModelLoteria.dineroTextEditor) },
                        modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp)) {
                        Text(text = "Apostar")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTextEditor(viewModelLoteria: LoteriasViewModel){
    Row() {
        TextField(value = viewModelLoteria.loteriaTextEditor,
            onValueChange = {viewModelLoteria.nuevaLoteria(it)},
            label ={ Text(text = "Loteria")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
        TextField(value = viewModelLoteria.dineroTextEditor,
            onValueChange = {viewModelLoteria.nuevoDinero(it)},
            label ={ Text(text = "Dinero apostado")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
    }
}

@Composable
fun PantallaBotonApostar(){
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
        Text(text = "Jugar loteria escrita")
    }
}

@Composable
fun PantallaTextos(uiState: LoteriasUiState){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(20.dp)) {
        Text(text = "${uiState.textoUltAccion}",
            Modifier
                .background(Color.White)
                .fillMaxWidth())
        Text(text = "${uiState.textoTotalVecesJugado}",
            Modifier
                .background(Color.Yellow)
                .fillMaxWidth())
        Text(text = "${uiState.textoTotalDineroGastado}",
            Modifier
                .background(Color.Cyan)
                .fillMaxWidth())
        Text(text = "${uiState.textoTotalDineroGanado}",
            Modifier
                .background(Color.Magenta)
                .fillMaxWidth())
    }
}
