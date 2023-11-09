@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.mirego.sample.ui.tv.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Carousel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.mirego.sample.viewmodels.showcase.components.carousel.CarouselItemContent
import com.mirego.sample.viewmodels.showcase.components.carousel.CarouselShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDImage

@ExperimentalTvMaterial3Api
@Composable
fun CarouselTvShowcaseView(carouselShowcaseViewModel: CarouselShowcaseViewModel) {
    val viewModel: CarouselShowcaseViewModel by carouselShowcaseViewModel.observeAsState()

    Carousel(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        itemCount = viewModel.items.size,
    ) { index ->
        CarouselItemView(viewModel.items[index])
    }
}

@Composable
private fun CarouselItemView(content: CarouselItemContent) {
    Box(modifier = Modifier.clip(RoundedCornerShape(16.dp))) {
        VMDImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .background(MaterialTheme.colorScheme.background),
            viewModel = content.image,
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
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
