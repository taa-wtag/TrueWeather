package com.rektstudios.trueweather.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily
import com.rektstudios.trueweather.presentation.viewmodels.CityViewModel

@Composable
fun CitiesScreen(
    onBackClick: () -> Unit,
    viewModel: CityViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val aspectRatio = with(configuration) { screenWidthDp.dp / screenHeightDp.dp }
    var isSearchModalVisible by remember { mutableStateOf(false) }
    val searchQuery by viewModel.searchQuery.collectAsState()

    val cityList by viewModel.cityCardDataList.collectAsState(emptyList())
    val suggestedCities by viewModel.suggestedCities.collectAsState()

    Scaffold { safePadding ->
        Box(
            modifier =
                modifier
                    .padding(safePadding)
                    .fillMaxSize()
                    .background(Color.White),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(top = 20.dp, bottom = 100.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(cityList.size) { index -> SmallCityCard(cityList[index], { viewModel.deleteCity(it) }) }
            }

            IconButton(
                onClick = onBackClick,
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp)
                        .shadow(elevation = 4.dp, shape = CircleShape)
                        .background(color = Color.White, shape = CircleShape)
                        .size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back",
                    tint = Color.Black,
                )
            }

            Button(
                onClick = { isSearchModalVisible = true },
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp, bottom = 24.dp),
                shape = RoundedCornerShape(8.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(30.dp)
                            .padding(end = 8.dp),
                )
                Text(
                    text = "Add City",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 6.dp),
                )
            }

            if (isSearchModalVisible) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                            ) {
                                isSearchModalVisible = false
                            },
                )

                Card(
                    modifier =
                        Modifier
                            .aspectRatio(aspectRatio * 1.5f)
                            .align(Alignment.Center)
                            .padding(20.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.searchCities(it) },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search Cities...", color = Color.Gray) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors =
                                OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.LightGray,
                                    focusedBorderColor = Color.Black,
                                ),
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(suggestedCities.size) { index ->
                                Text(
                                    text = suggestedCities[index],
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.addCity(suggestedCities[index])
                                                isSearchModalVisible = false
                                            }.padding(vertical = 12.dp, horizontal = 8.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Cities Screen", showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun CitiesScreenPreview() {
    CitiesScreen({})
}
