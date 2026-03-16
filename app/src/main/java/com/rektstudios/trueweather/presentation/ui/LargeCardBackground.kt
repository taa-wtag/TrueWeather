package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LargeCardBackground(
    color: CardGradientBackgroundColor,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val viewportWidth = 79.375f
        val viewportHeight = 47.625f

        val scaleX = width / viewportWidth
        val scaleY = height / viewportHeight

        drawRect(color = color.background)

        val path2 =
            Path().apply {
                moveTo(0f, 47.625f * scaleY)
                lineTo(0f, 0.794f * scaleY)
                cubicTo(
                    24.129f * scaleX,
                    0.852f * scaleY,
                    52.835f * scaleX,
                    0.615f * scaleY,
                    79.375f * scaleX,
                    19.315f * scaleY,
                )
                lineTo(79.375f * scaleX, 47.625f * scaleY)
                close()
            }
        drawPath(path = path2, color = color.first)

        // Path 3: Second wave
        val path3 =
            Path().apply {
                moveTo(0f, 47.625f * scaleY)
                lineTo(0f, 6.085f * scaleY)
                cubicTo(
                    16.201f * scaleX,
                    5.791f * scaleY,
                    50.72f * scaleX,
                    10.934f * scaleY,
                    79.375f * scaleX,
                    29.633f * scaleY,
                )
                lineTo(79.375f * scaleX, 47.625f * scaleY)
                close()
            }
        drawPath(path = path3, color = color.second)

        val path4 =
            Path().apply {
                moveTo(0f, 47.625f * scaleY)
                lineTo(0f, 10.583f * scaleY)
                relativeCubicTo(
                    19.637f * scaleX,
                    1.38f * scaleY,
                    53.275f * scaleX,
                    9.966f * scaleY,
                    79.375f * scaleX,
                    30.692f * scaleY,
                )
                lineTo(79.375f * scaleX, 47.625f * scaleY)
                close()
            }
        drawPath(path = path4, color = color.third)
    }
}

@Preview
@Composable
fun LargeCardBackgroundPreview() {
    LargeCardBackground(
        color = CardGradientBackgroundColor.Purple,
        modifier = Modifier.fillMaxSize(),
    )
}

