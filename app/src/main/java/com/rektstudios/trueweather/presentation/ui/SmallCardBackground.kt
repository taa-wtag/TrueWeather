package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SmallCardBackground(
    color: CardGradientBackgroundColor,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val viewportWidth = 52.917f
        val viewportHeight = 58.208f

        val scaleX = width / viewportWidth
        val scaleY = height / viewportHeight

        drawRect(color = color.background)

        val path2 =
            Path().apply {
                moveTo(52.917f * scaleX, 58.191f * scaleY)
                lineTo(0f, 58.191f * scaleY)
                lineTo(0f, 2.471f * scaleY)
                relativeCubicTo(
                    0f * scaleX,
                    0f * scaleY,
                    8.956f * scaleX,
                    -1.027f * scaleY,
                    17.167f * scaleX,
                    3.854f * scaleY,
                )
                relativeCubicTo(
                    9.988f * scaleX,
                    5.936f * scaleY,
                    20.106f * scaleX,
                    11.099f * scaleY,
                    35.75f * scaleX,
                    12.244f * scaleY,
                )
                close()
            }
        drawPath(path = path2, color = color.first)

        val path3 =
            Path().apply {
                moveTo(52.917f * scaleX, 58.208f * scaleY)
                lineTo(0f, 58.208f * scaleY)
                lineTo(0f, 12.525f * scaleY)
                relativeCubicTo(
                    0f * scaleX,
                    0f * scaleY,
                    8.956f * scaleX,
                    -1.027f * scaleY,
                    17.167f * scaleX,
                    3.854f * scaleY,
                )
                relativeCubicTo(
                    9.988f * scaleX,
                    5.936f * scaleY,
                    20.106f * scaleX,
                    11.099f * scaleY,
                    35.75f * scaleX,
                    12.244f * scaleY,
                )
                close()
            }
        drawPath(path = path3, color = color.second)

        val path4 =
            Path().apply {
                moveTo(52.917f * scaleX, 58.208f * scaleY)
                lineTo(0f, 58.208f * scaleY)
                lineTo(0f, 23.109f * scaleY)
                relativeCubicTo(
                    0f * scaleX,
                    0f * scaleY,
                    8.956f * scaleX,
                    -1.027f * scaleY,
                    17.167f * scaleX,
                    3.854f * scaleY,
                )
                relativeCubicTo(
                    9.988f * scaleX,
                    5.936f * scaleY,
                    20.106f * scaleX,
                    11.099f * scaleY,
                    35.75f * scaleX,
                    12.244f * scaleY,
                )
                close()
            }
        drawPath(path = path4, color = color.third)
    }
}

@Preview
@Composable
fun SmallCardBackgroundPreview() {
    SmallCardBackground(
        color = CardGradientBackgroundColor.Purple,
        modifier = Modifier.fillMaxSize(),
    )
}
