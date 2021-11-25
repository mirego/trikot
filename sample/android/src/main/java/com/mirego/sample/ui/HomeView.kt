package com.mirego.sample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirego.sample.R
import com.mirego.sample.viewmodels.home.HomeViewModel
import com.mirego.sample.viewmodels.home.listItem.HomeListItemViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMText

@Composable
fun HomeView(homeViewModel: HomeViewModel) {
    val viewModel: HomeViewModel by homeViewModel.observeAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })

        VMText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            viewModel = viewModel.title,
            fontSize = 22.sp
        )

        LazyColumn {
            items(viewModel.items) { item ->
                HomeItemView(item)
            }
        }
    }
}

@Composable
fun HomeItemView(item: HomeListItemViewModel) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp)
            .fillMaxWidth(),
        elevation = 3.dp,
        backgroundColor = Color.LightGray
    ) {
        VMText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp),
            viewModel = item.name,
            fontSize = 18.sp
        )
    }
}
