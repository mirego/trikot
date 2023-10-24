package com.mirego.sample.ui.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mirego.sample.viewmodels.tv.HomeTvViewModel

@Composable
fun HomeTvView(homeTvViewModel: HomeTvViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Text("TV HOME")
    }
}
