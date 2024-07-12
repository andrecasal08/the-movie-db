package com.acdevs.themoviedb.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MovieScreen(modifier: Modifier = Modifier
    .fillMaxSize()) {
    LazyColumn() {
        item {
            AsyncImage(
                model = "https://www.adrenaline.com.br/wp-content/uploads/2023/09/marvels-spiderman-remastered-912x569.jpg",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                contentScale = ContentScale.FillBounds
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Popular Movies",
                    modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null)
                }
            }
        }

        item {
            Row(
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
            ) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null)
                Text(text = "8.4/10",
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal)
            }
        }

        item {
            LazyRow {
                items(5) { item ->
                    Card(
                        modifier = Modifier.padding(16.dp, 15.dp, 0.dp, 0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                    ) {
                        Text(text = "category",
                            fontSize = 15.sp, modifier = Modifier.padding(3.dp, 3.dp, 3.dp, 3.dp))
                    }
                }
            }
        }

        item {
            Text(text = "Description",
                modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        }

        item {
            Text(text = "asdasdasdasdasdasdasasdasdasdasdasdasdasasdasdasdasdasdasdasasdasdasdasdasdasdasasdasdasdasdasdasdasasdasdasdasdasdasdasasdasdasdasdasdasdasasdasdasdasdasdasdasasdasdasdasdasdasdas",
                modifier = Modifier.padding(16.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal)
        }

        item {
            Text(text = "Cast",
                modifier = Modifier.padding(16.dp, 30.dp, 0.dp, 0.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        }

        item {
            LazyRow {
                items(5) { item ->
                    Card(
                        modifier = Modifier.padding(16.dp, 15.dp, 0.dp, 0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                    ) {
                        AsyncImage(
                            model = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/640px-Tom_Holland_by_Gage_Skidmore.jpg",
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .aspectRatio(1.5f),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}