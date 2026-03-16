package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ControlCamera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rektstudios.trueweather.R
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily
import com.rektstudios.trueweather.presentation.ui.theme.WeatherHourSelectedBackground
import com.rektstudios.trueweather.presentation.ui.theme.WeatherHourSelectedForeground

@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // State for the SwipeRefreshLayout equivalent
    var isRefreshing by remember { mutableStateOf(false) }

    // Pager state (mocking 3 pages for the city cards)
    val pageCount = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = CircleShape,
                containerColor = WeatherHourSelectedBackground,
                contentColor = WeatherHourSelectedForeground,
            ) {
                Icon(
                    imageVector = Icons.Filled.ControlCamera,
                    contentDescription = "Get Location",
                )
            }
        },
    ) { scaffoldPadding ->

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                // In a real app, set isRefreshing = false when data loads
            },
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.6f),
                ) { page ->
                    if (page < pagerState.pageCount - 1) {
                        LargeCityCard("BD", "BD", "30", "Sunny", "Today", CardGradientBackgroundColor.Purple, R.drawable.ic_image)
                    } else {
                        LargeAddCityCard(onAddClick)
                    }
                }

                // 2. TabLayout Equivalent (Dot Indicators)
                PagerIndicator(
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                )

                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    contentPadding = PaddingValues(bottom = 10.dp),
                ) {
                    item {
                        Text(
                            text = "Today",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier =
                                modifier.padding(
                                    start = 20.dp,
                                    top = 10.dp,
                                    bottom = 15.dp,
                                ),
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 20.dp),
                        ) {
                            items(20) { index ->
                                WeatherHourCard("22:00", "Sunny", index == 0, R.drawable.ic_image)
                            }
                        }
                        Text(
                            text = "Next 2 Days",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier =
                                modifier.padding(
                                    start = 20.dp,
                                    top = 30.dp,
                                    bottom = 10.dp,
                                ),
                        )
                    }
                    items(20) { index ->
                        WeatherDayItem("Wednesday $index", R.drawable.ic_image, "22", "27")
                    }
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) Color.DarkGray else Color.LightGray
            val size = if (currentPage == iteration) 10.dp else 8.dp

            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(size),
            )
        }
    }
}

@Preview(name = "Home Screen", showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onAddClick = {},
    )
}
