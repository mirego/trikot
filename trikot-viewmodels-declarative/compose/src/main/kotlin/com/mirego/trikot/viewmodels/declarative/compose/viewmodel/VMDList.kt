package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent

@Composable
fun <C : VMDIdentifiableContent> VMDList(
    modifier: Modifier = Modifier,
    viewModel: VMDListViewModel<C>,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    itemContent: @Composable LazyItemScope.(item: C) -> Unit
) {
    val listViewModel: VMDListViewModel<C> by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    LazyColumn(
        modifier = modifier
            .hidden(listViewModel.isHidden),
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior
    ) {
        items(listViewModel.elements, key = { item -> item.identifier }, itemContent = itemContent)
    }
}
