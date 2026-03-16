package com.rektstudios.trueweather.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rektstudios.trueweather.R
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily
import com.rektstudios.trueweather.presentation.ui.theme.WeatherHourSelectedBackground
import com.rektstudios.trueweather.presentation.ui.theme.WeatherHourSelectedForeground

@Composable
fun WeatherHourCard(
    time: String,
    condition: String,
    isCurrent: Boolean = false,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .padding(
                    start = 10.dp,
                    top = 5.dp,
                    end = 10.dp,
                    bottom = 5.dp,
                ).defaultMinSize(minWidth = 60.dp, minHeight = 110.dp)
                .aspectRatio(6f / 11f, true),
        shape = RoundedCornerShape(10.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = if (isCurrent) WeatherHourSelectedBackground else Color.White,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = time,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (isCurrent) WeatherHourSelectedForeground else Color.LightGray,
            )

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = condition,
                contentScale = ContentScale.FillWidth,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
            )

            Text(
                text = condition,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (isCurrent) WeatherHourSelectedForeground else Color.LightGray,
            )
        }
    }
}

@Preview(
    name = "Weather Hour Card (Current)",
    showBackground = true,
    backgroundColor = 0xFFE0E0E0,
    widthDp = 400,
    heightDp = 300,
)
@Composable
fun WeatherHourCardCurrentPreview() {
    WeatherHourCard(
        time = "02:00",
        condition = "Blizzard",
        isCurrent = true,
        iconRes = R.drawable.ic_image,
        modifier = Modifier.padding(16.dp),
    )
}

@Preview(
    name = "Weather Hour Card (Default)",
    showBackground = true,
    backgroundColor = 0xFFE0E0E0,
    widthDp = 400,
    heightDp = 300,
)
@Composable
fun WeatherHourCardDefaultPreview() {
    WeatherHourCard(
        time = "02:00",
        condition = "Blizzard",
        isCurrent = false,
        iconRes = R.drawable.ic_image,
        modifier = Modifier.padding(16.dp),
    )
}
