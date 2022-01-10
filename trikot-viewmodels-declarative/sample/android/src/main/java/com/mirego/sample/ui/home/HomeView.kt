package com.mirego.sample.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirego.sample.R
import com.mirego.sample.viewmodels.home.HomeViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDList
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun HomeView(homeViewModel: HomeViewModel) {
    val viewModel: HomeViewModel by homeViewModel.observeAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 32.dp),
            viewModel = viewModel.title,
            fontSize = 22.sp
        )

        Divider(color = Color.LightGray)

        VMDList(viewModel = viewModel.items) { item ->
            VMDButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                viewModel = item.content,
                content = {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                        text = item.content.content.text,
                        fontSize = 18.sp
                    )
                }
            )
            Divider(color = Color.LightGray)
        }
    }
}
