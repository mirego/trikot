package com.mirego.sample.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirego.sample.viewmodels.home.HomeViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDSectionedList
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(homeViewModel: HomeViewModel) {
    val viewModel: HomeViewModel by homeViewModel.observeAsState()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(title = { Text(text = viewModel.title) })
        }
    ) { paddingValues ->

        VMDSectionedList(viewModel = viewModel.sections, modifier = Modifier.padding(paddingValues)) { sections ->
            sections.forEach { section ->
                stickyHeader {
                    VMDText(
                        modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 16.dp),
                        viewModel = section.title,
                        fontSize = 22.sp
                    )
                    HorizontalDivider(color = Color.LightGray)
                }

                items(section.elements, key = { item -> item.identifier }) { element ->
                    VMDButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        viewModel = element.button,
                        content = { textContent ->
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                                text = textContent.text,
                                fontSize = 18.sp
                            )
                        }
                    )
                    HorizontalDivider(color = Color.LightGray)
                }
            }
        }
    }
}
