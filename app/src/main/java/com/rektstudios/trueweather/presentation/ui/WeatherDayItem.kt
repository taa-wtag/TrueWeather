package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rektstudios.trueweather.domain.data.DailyWeatherData
import com.rektstudios.trueweather.presentation.ui.theme.DarkGrey
import com.rektstudios.trueweather.presentation.ui.theme.LightGrey
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily

@Composable
fun WeatherDayItem(
    data: DailyWeatherData,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
    ) {
        Text(
            text = data.dayOfWeek,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = DarkGrey,
        )

        AsyncImage(
            model = data.imageUrl,
            contentDescription = null,
            modifier =
                Modifier
                    .defaultMinSize(minWidth = 40.dp, minHeight = 40.dp)
                    .weight(1f),
        )

        Text(
            text = "${data.maxTemp}° / ",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = DarkGrey,
        )
        Text(
            text = "${data.minTemp}°",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = LightGrey,
        )
    }
}

@Preview(name = "Weather Day Item", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun WeatherDayItemPreview() {
    WeatherDayItem(
        DailyWeatherData("Wednesday", "27", "21", ""),
    )
}
