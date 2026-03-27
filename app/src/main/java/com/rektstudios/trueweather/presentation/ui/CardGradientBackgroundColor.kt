package com.rektstudios.trueweather.presentation.ui

import androidx.compose.ui.graphics.Color

sealed class CardGradientBackgroundColor {
    abstract val background: Color
    abstract val first: Color
    abstract val second: Color
    abstract val third: Color

    data object Blue : CardGradientBackgroundColor() {
        override val background: Color = Color(0xFF8ACDE0)
        override val first: Color = Color(0xFF83CAE0)
        override val second: Color = Color(0xFF72C2DD)
        override val third: Color = Color(0xFF52B6D6)
    }

    data object Red : CardGradientBackgroundColor() {
        override val background: Color = Color(0xFFd92e4e)
        override val first: Color = Color(0xFFd42949)
        override val second: Color = Color(0xFFcb2040)
        override val third: Color = Color(0xFFb9102f)
    }

    data object Purple : CardGradientBackgroundColor() {
        override val background: Color = Color(0xFF8c78f5)
        override val first: Color = Color(0xFF8974f5)
        override val second: Color = Color(0xFF846df9)
        override val third: Color = Color(0xFF7b61ff)
    }

    data object Green : CardGradientBackgroundColor() {
        override val background: Color = Color(0xFFa2ffb3)
        override val first: Color = Color(0xFF80f99a)
        override val second: Color = Color(0xFF58f470)
        override val third: Color = Color(0xFF00e730)
    }

    data object Orange : CardGradientBackgroundColor() {
        override val background: Color = Color(0xFFffc9a2)
        override val first: Color = Color(0xFFf9b280)
        override val second: Color = Color(0xFFf49958)
        override val third: Color = Color(0xFFe75f00)
    }

    data object Violet : CardGradientBackgroundColor() {
        override val background: Color = Color(0xFFa4bbf6)
        override val first: Color = Color(0xFF91adf9)
        override val second: Color = Color(0xFF7195f6)
        override val third: Color = Color(0xFF5781f0)
    }

    companion object {
        fun getColor(value: Int) =
            when (value) {
                1 -> Blue
                2 -> Red
                3 -> Violet
                4 -> Green
                5 -> Orange
                else -> Purple
            }
    }
}
