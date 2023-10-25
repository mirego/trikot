@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.mirego.sample.ui.tv


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.Text
import com.mirego.sample.ui.tv.showcase.TextTvShowcaseView
import com.mirego.sample.ui.tv.showcase.ToggleTvShowcaseView
import com.mirego.sample.viewmodels.tv.HomeMenuSectionItem
import com.mirego.sample.viewmodels.tv.HomeTvViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState

@ExperimentalTvMaterial3Api
@Composable
fun HomeTvView(homeTvViewModel: HomeTvViewModel) {
    val viewModel: HomeTvViewModel by homeTvViewModel.observeAsState()

    val selectedIndex = remember { mutableIntStateOf(0) }

    NavigationDrawer(
        drawerContent = { drawerValue ->
            OpenDrawerMenuView(
                viewModel = viewModel,
                selectedIndex = selectedIndex,
                drawerValue = drawerValue
            )
        }
    ) {
        HomeContentView(viewModel, selectedIndex)
    }
}

@Composable
private fun HomeContentView(viewModel: HomeTvViewModel, selectedIndex: MutableState<Int>) {
    when (val sectionViewModel = viewModel.menuItems[selectedIndex.value]) {
        is HomeMenuSectionItem.TextShowcase -> TextTvShowcaseView(sectionViewModel.viewModel)
        is HomeMenuSectionItem.ToggleShowcase -> ToggleTvShowcaseView(sectionViewModel.viewModel)
    }
}

@Composable
private fun OpenDrawerMenuView(viewModel: HomeTvViewModel, selectedIndex: MutableState<Int>, drawerValue: DrawerValue) {
    val drawerWidth by animateDpAsState(
        targetValue = when (drawerValue) {
            DrawerValue.Closed -> 48.dp
            DrawerValue.Open -> 200.dp
        }, label = "drawerWidth"
    )
    Column(
        Modifier
            .background(Color.Gray)
            .width(drawerWidth)
            .fillMaxHeight()
            .padding(12.dp)
            .selectableGroup(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        viewModel.menuItems.forEachIndexed { index, homeMenuSectionItem ->
            NavigationDrawerItem(
                selected = selectedIndex.value == index,
                onClick = { selectedIndex.value = index },
                label = {
                    AnimatedVisibility(visible = drawerValue == DrawerValue.Open) {
                        Text(homeMenuSectionItem.title)
                    }
                }
            )
        }
    }
}


