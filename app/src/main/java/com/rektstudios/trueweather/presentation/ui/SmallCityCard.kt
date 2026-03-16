package com.rektstudios.trueweather.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rektstudios.trueweather.R
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily

@Composable
fun SmallCityCard(
    cityName: String,
    countryName: String,
    temperature: String,
    condition: String,
    backgroundColor: CardGradientBackgroundColor,
    @DrawableRes conditionIconRes: Int,
    isDeleteVisible: Boolean = false,
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Card(
            modifier =
                Modifier
                    .padding(10.dp)
                    .aspectRatio(9f / 11f, true)
                    .defaultMinSize(minWidth = 180.dp, minHeight = 220.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                SmallCardBackground(
                    backgroundColor,
                    Modifier.fillMaxSize(),
                )

                Column(
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 28.dp, top = 26.dp),
                ) {
                    Text(
                        text = cityName,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                    Text(
                        text = countryName,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.White,
                    )
                }

                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = temperature,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
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

                Row(
                    modifier =
                        Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp, bottom = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = conditionIconRes),
                        contentDescription = condition,
                        modifier = Modifier.size(35.dp),
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = condition,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                }
            }
        }

        if (isDeleteVisible) {
            IconButton(
                onClick = onDeleteClick,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 4.dp, top = 4.dp)
                        .size(26.dp)
                        .shadow(elevation = 5.dp, shape = CircleShape)
                        .background(Color.Red, shape = CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete City",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Preview(
    name = "City Card (Default)",
    showBackground = true,
    backgroundColor = 0xFFE0E0E0,
    widthDp = 400,
    heightDp = 300,
)
@Composable
fun SmallCityCardPreview() {
    SmallCityCard(
        cityName = "London",
        countryName = "United Kingdom",
        temperature = "18",
        condition = "Cloudy",
        backgroundColor = CardGradientBackgroundColor.Red,
        conditionIconRes = R.drawable.ic_image,
        isDeleteVisible = false,
    )
}

@Preview(
    name = "City Card (Edit Mode)",
    showBackground = true,
    backgroundColor = 0xFFE0E0E0,
    widthDp = 400,
    heightDp = 300,
)
@Composable
fun SmallCityCardEditModePreview() {
    SmallCityCard(
        cityName = "Tokyo",
        countryName = "Japan",
        temperature = "24",
        condition = "Sunny",
        backgroundColor = CardGradientBackgroundColor.Blue,
        conditionIconRes = R.drawable.ic_image,
        isDeleteVisible = true,
    )
}
