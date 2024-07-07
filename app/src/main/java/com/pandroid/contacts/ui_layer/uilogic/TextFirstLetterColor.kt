package com.pandroid.contacts.ui_layer.uilogic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random


val colorClasses = listOf(
    Color(0xFFE57373), // Red
    Color(0xFF81C784), // Green
    Color(0xFF64B5F6), // Blue
    Color(0xFFFFD54F), // Yellow
    Color(0xFFBA68C8)  // Purple
)

@Composable
fun RandomLetterImage(text: String) {
    val firstLetter = text.firstOrNull()?.toString() ?: ""
    val backgroundColor = getRandomColorClass()

    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(backgroundColor)

    ) {
        Canvas(modifier = Modifier.size(60.dp)) {
            val paint = Paint().asFrameworkPaint().apply {
                isAntiAlias = true
                textSize = 30.sp.toPx()
                color = android.graphics.Color.WHITE // Text color
                textAlign = android.graphics.Paint.Align.CENTER
            }

            drawContext.canvas.nativeCanvas.drawText(
                firstLetter,
                size.width / 2,
                size.height / 2 - (paint.descent() + paint.ascent()) / 2,
                paint
            )
        }
    }
}

fun getRandomColorClass(): Color {
    val randomIndex = Random.nextInt(colorClasses.size)
    return colorClasses[randomIndex]
}
