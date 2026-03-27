package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rektstudios.trueweather.domain.data.CityCardData
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily

@Composable
fun SmallCityCard(
    data: CityCardData,
    onDeleteClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var isDeleteVisible by remember { mutableStateOf(false) }

    Box(
        modifier =
            modifier.combinedClickable(
                onLongClick = { isDeleteVisible = !isDeleteVisible },
                onDoubleClick = {},
                hapticFeedbackEnabled = true,
                onClick = {},
            ),
    ) {
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
                    CardGradientBackgroundColor.getColor(data.backgroundColor),
                    Modifier.fillMaxSize(),
                )

                Column(
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 28.dp, top = 26.dp),
                ) {
                    Text(
                        text = data.cityName,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                    Text(
                        text = data.countryName,
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
                        text = data.apparentTemp,
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
                    AsyncImage(
                        model = data.imageUrl,
                        contentDescription = data.mediumCondition,
                        modifier = Modifier.size(35.dp),
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = data.shortCondition,
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
                onClick = {
                    onDeleteClick(data.name)
                    isDeleteVisible = false
                },
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 4.dp, top = 4.dp)
                        .size(32.dp)
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
        CityCardData(
            name = "London, United Kingdom",
            fullDate = "",
            temp = "18",
            apparentTemp = "28",
            condition = "Cloudy",
            imageUrl = "",
            backgroundColor = 2,
        ),
    )
}
