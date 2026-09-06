package com.example.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun RateAppDialog(
    onDismissRequest: () -> Unit,
    onRateClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Olá amiguinho! 🌟",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0288D1)
            )
        },
        text = {
            Text(
                text = "Estás a gostar de brincar com o Zé Traquina? Pedimos aos teus papás para deixarem uma estrelinha na loja, assim podemos continuar a criar aventuras mágicas! 😊",
                fontSize = 16.sp
            )
        },
        confirmButton = {
            TextButton(onClick = onRateClicked) {
                Text("Avaliar!", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Agora não")
            }
        }
    )
}
