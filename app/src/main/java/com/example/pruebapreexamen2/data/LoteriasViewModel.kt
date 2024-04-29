package com.example.pruebapreexamen2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class LoteriasViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoteriasUiState())
    val uiState: StateFlow<LoteriasUiState> = _uiState.asStateFlow()

    var loteriaTextEditor by mutableStateOf("")
    var dineroTextEditor by mutableStateOf("")

    fun nuevaLoteria(nuevaloteriaTextEditor: String) {
        loteriaTextEditor = nuevaloteriaTextEditor
    }

    fun nuevoDinero(nuevodineroTextEditor: String) {
        dineroTextEditor = nuevodineroTextEditor
    }

    fun ApostarLoteria(loteria: LoteriaTipo, loterias: ArrayList<LoteriaTipo>, dineroJugado: String) {
        var dineroApostado = dineroJugado.toInt()
        var numJugado: Int = Random.nextInt(1, 4)
        var numGanador: Int = Random.nextInt(1, 4)
        var textoUltAccionAct = ""
        var textoTotalVecesJugadoAct = ""
        var textoTotalDineroGastadoAct = ""
        var textoTotalDineroGanadoAct = ""
        var contadorAct = _uiState.value.contador
        var dineroGastadoAct = _uiState.value.dineroGastado

        if (dineroApostado > 0) {
            for (boleto in loterias) {
                if (boleto.nombre == loteria.nombre) {
                    contadorAct++
                    textoUltAccionAct = "Has jugado $dineroApostado € a ${boleto.nombre}"
                    textoTotalVecesJugadoAct = "Has jugado $contadorAct veces a la loteria"
                    dineroGastadoAct += dineroApostado
                    textoTotalDineroGastadoAct = "Has gastado $dineroGastadoAct en loteria"

                    if (numJugado == numGanador) {
                        textoTotalDineroGanadoAct = "Has ganado ${dineroApostado * boleto.premio} €"
                    } else {
                        textoTotalDineroGanadoAct = "Has perdido ${dineroApostado} €"
                    }
                    _uiState.update { actualizacionUiState ->
                        actualizacionUiState.copy(
                            contador = contadorAct,
                            textoUltAccion = textoUltAccionAct,
                            textoTotalVecesJugado = textoTotalVecesJugadoAct,
                            dineroGastado = dineroGastadoAct,
                            textoTotalDineroGastado = textoTotalDineroGastadoAct,
                            textoTotalDineroGanado = textoTotalDineroGanadoAct
                        )
                    }
                }
            }
        } else {
            textoUltAccionAct = "No se puede comprar una lotería con 0 €"
            _uiState.update { actualizacionUiState ->
                actualizacionUiState.copy(
                    contador = contadorAct,
                    textoUltAccion = textoUltAccionAct,
                    textoTotalVecesJugado = textoTotalVecesJugadoAct,
                    dineroGastado = dineroGastadoAct,
                    textoTotalDineroGastado = textoTotalDineroGastadoAct,
                    textoTotalDineroGanado = textoTotalDineroGanadoAct
                )
            }
        }
    }

    fun ApostarLoteriaTextEditor(loterias: ArrayList<LoteriaTipo>, loteriaTextEditor: String,
                                 dineroJugado: String){
        var textoUltAccionAct = ""
        var existe = false
        var loteria = LoteriaTipo("", 0)

        for (boleto in loterias){
            if (boleto.nombre.equals(loteriaTextEditor, ignoreCase = true)){
                existe = true
                loteria = boleto.copy()
            }
        }
        if(existe){
            ApostarLoteria(loteria, loterias, dineroJugado)
        }else{
            textoUltAccionAct = "No existe ninguna loteria con ese nombre"
            _uiState.update { actualizacionUiState ->
                actualizacionUiState.copy(
                    textoUltAccion = textoUltAccionAct
                )
            }
        }
    }
}