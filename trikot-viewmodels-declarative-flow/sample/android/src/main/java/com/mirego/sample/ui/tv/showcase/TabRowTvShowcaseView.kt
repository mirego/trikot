@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.mirego.sample.ui.tv.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import com.mirego.sample.viewmodels.showcase.components.carousel.CarouselItemContent
import com.mirego.sample.viewmodels.showcase.components.carousel.CarouselShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDImage

@ExperimentalTvMaterial3Api
@Composable
fun TabRowTvShowcaseView(carouselShowcaseViewModel: CarouselShowcaseViewModel) {
    val viewModel: CarouselShowcaseViewModel by carouselShowcaseViewModel.observeAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        var selectedTabIndex by remember { mutableStateOf(0) }
        TabRow(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            selectedTabIndex = selectedTabIndex
        ) {
            viewModel.items.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex == index,
                    onFocus = {
                        selectedTabIndex = index
                    },
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        text = item.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
        RowItemView(viewModel.items[selectedTabIndex])
    }
}

@Composable
private fun RowItemView(content: CarouselItemContent) {
    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .padding(top = 90.dp, bottom = 32.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        VMDImage(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            viewModel = content.image,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.3f))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = content.title,
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = content.description,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
