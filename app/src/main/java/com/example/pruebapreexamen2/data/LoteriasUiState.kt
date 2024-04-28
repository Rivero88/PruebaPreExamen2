package com.example.pruebapreexamen2.data

data class LoteriasUiState(
    var textoUltAccion: String = "No has jugado ninguna loteria",
    var textoTotalVecesJugado: String = "Has jugado 0 veces a la loteria",
    var textoTotalDineroGastado: String = " Has gastado 0 € en loteria",
    var textoTotalDineroGanado: String = "Has ganado 0 € en loteria",
    var contador: Int = 0,
    var dineroGastado: Int = 0
)
