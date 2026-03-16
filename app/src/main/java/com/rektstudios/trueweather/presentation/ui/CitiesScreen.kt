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
import com.rektstudios.trueweather.R
import com.rektstudios.trueweather.presentation.ui.theme.PoppinsFontFamily

@Composable
fun CitiesScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val aspectRatio = with(configuration) { screenWidthDp.dp / screenHeightDp.dp }
    var isSearchModalVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold { safePadding ->
        Box(modifier = modifier.padding(safePadding).fillMaxSize()) {
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
                items(10) { _ ->
                    SmallCityCard(
                        "BD",
                        "BD",
                        "30",
                        "Sunny",
                        CardGradientBackgroundColor.Purple,
                        R.drawable.ic_image,
                    )
                }
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
                            onValueChange = { searchQuery = it },
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
                            items(5) { suggestion ->
                                Text(
                                    text = "Suggestion $suggestion",
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp, horizontal = 8.dp),
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
