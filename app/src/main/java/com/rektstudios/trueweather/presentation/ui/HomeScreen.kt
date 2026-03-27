package com.rektstudios.trueweather.presentation.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily
import com.rektstudios.trueweather.presentation.ui.theme.WeatherHourSelectedBackground
import com.rektstudios.trueweather.presentation.ui.theme.WeatherHourSelectedForeground
import com.rektstudios.trueweather.presentation.viewmodels.WeatherViewModel

@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    viewModel: WeatherViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    var isRefreshing by remember { mutableStateOf(false) }

    val cityList by viewModel.cityCardList.collectAsState()
    val cityState by viewModel.currentCityState.collectAsState()

    val pagerState = rememberPagerState { cityList.size + 1 }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { index ->
            viewModel.setCurrentCity(if (index < pagerState.pageCount - 1) cityList[index].name else "")
        }
    }

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val settingResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
        ) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                viewModel.setCurrentCityFromGPS()
            } else {
                Toast.makeText(context, "Refused to turn on Location", Toast.LENGTH_SHORT).show()
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            hasLocationPermission = isGranted
            if (isGranted) {
                checkLocationSetting(
                    context = context,
                    onDisabled = { intentSenderRequest ->
                        settingResultLauncher.launch(intentSenderRequest)
                    },
                    onEnabled = {
                        viewModel.setCurrentCityFromGPS()
                    },
                )
            }
        }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!hasLocationPermission) {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        checkLocationSetting(
                            context = context,
                            onDisabled = { intentSenderRequest ->
                                settingResultLauncher.launch(intentSenderRequest)
                            },
                            onEnabled = {
                                viewModel.setCurrentCityFromGPS()
                            },
                        )
                    }
                },
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
                viewModel.refreshWeatherData { isRefreshing = false }
            },
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .background(Color.White),
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
                        LargeCityCard(cityList[page])
                    } else {
                        LargeAddCityCard(onAddClick)
                    }
                }

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
                            val list = cityState.hourlyWeatherCardData
                            items(list.size) { index -> WeatherHourCard(list[index], index == 0) }
                            if (list.isEmpty()) {
                                item { WeatherHourPlaceHolderCard() }
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
                    val list = cityState.dailyWeatherData
                    items(list.size) { index -> WeatherDayItem(list[index]) }
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
