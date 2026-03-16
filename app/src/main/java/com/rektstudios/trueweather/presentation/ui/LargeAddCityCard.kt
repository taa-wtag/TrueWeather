package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LargeAddCityCard(
    onAddCityClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .defaultMinSize(minWidth = 300.dp, minHeight = 200.dp)
                .padding(
                    start = 40.dp,
                    top = 20.dp,
                    end = 40.dp,
                    bottom = 20.dp,
                ).aspectRatio(1.5f),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onAddCityClick,
            modifier = Modifier.size(120.dp),
            shape = RoundedCornerShape(20.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE6E5E5),
                    contentColor = Color.DarkGray,
                ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add City",
                modifier = Modifier.size(80.dp),
            )
        }
    }
}

@Preview(
    name = "Large Add City Card",
    showBackground = true,
    widthDp = 400,
    heightDp = 300,
)
@Composable
fun LargeAddCityCardPreview() {
    LargeAddCityCard(
        onAddCityClick = { /* Do nothing in preview */ },
    )
}
