package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherHourPlaceHolderCard(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .padding(
                    start = 10.dp,
                    top = 5.dp,
                    end = 10.dp,
                    bottom = 5.dp,
                ).defaultMinSize(minWidth = 60.dp, minHeight = 110.dp)
                .aspectRatio(6f / 11f, true),
    ) {
    }
}

@Preview(
    name = "Weather Hour Card (Default)",
    showBackground = true,
    backgroundColor = 0xFFE0E0E0,
    widthDp = 400,
    heightDp = 300,
)
@Composable
fun WeatherHourPlaceHolderCardPreview() {
    WeatherHourPlaceHolderCard(
        modifier = Modifier.padding(16.dp),
    )
}
