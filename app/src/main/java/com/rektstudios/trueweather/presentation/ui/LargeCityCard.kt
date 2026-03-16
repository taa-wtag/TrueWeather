package com.rektstudios.trueweather.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rektstudios.trueweather.R
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily

@Composable
fun LargeCityCard(
    cityName: String,
    countryName: String,
    temperature: String,
    condition: String,
    date: String,
    backgroundColor: CardGradientBackgroundColor,
    @DrawableRes conditionIconRes: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    horizontal = 40.dp,
                    vertical = 20.dp,
                ).aspectRatio(1.5f),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .defaultMinSize(minWidth = 300.dp, minHeight = 200.dp),
        ) {
            LargeCardBackground(color = backgroundColor, modifier = Modifier.fillMaxSize())

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(20.dp),
            ) {
                Text(
                    text = cityName,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxSize(),
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = modifier.fillMaxHeight(),
                    ) {
                        Text(
                            text = countryName,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                        )
                        Row(
                            modifier =
                                Modifier
                                    .padding(start = 20.dp)
                                    .weight(2f),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Text(
                                text = temperature,
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 38.sp,
                                color = Color.White,
                            )
                            Text(
                                text = "°",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 34.sp,
                                color = Color.White,
                            )
                        }

                        Text(
                            text = date,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,
                            color = Color.White,
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Image(
                            painter = painterResource(id = conditionIconRes),
                            contentDescription = condition,
                            modifier = Modifier.size(100.dp),
                        )
                        Text(
                            text = condition,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Large City Card",
    showBackground = true,
    backgroundColor = 0xFFE0E0E0,
    widthDp = 400,
    heightDp = 300,
)
@Composable
fun LargeCityCardPreview() {
    LargeCityCard(
        cityName = "New York",
        countryName = "United States",
        temperature = "12",
        condition = "Partly Cloudy",
        date = "Mon, 16 Mar",
        backgroundColor = CardGradientBackgroundColor.Orange,
        conditionIconRes = R.drawable.ic_image,
    )
}
