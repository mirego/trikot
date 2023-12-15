package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.trikot.viewmodels.declarative.components.VMDListViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.hidden
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.content.VMDIdentifiableContent

@Composable
fun <C : VMDIdentifiableContent> VMDLazyRow(
    viewModel: VMDListViewModel<C>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal =
        if (!reverseLayout) Arrangement.Start else Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    itemContent: @Composable LazyItemScope.(item: C) -> Unit
) {
    VMDLazyRowIndexed(
        modifier = modifier,
        viewModel = viewModel,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        flingBehavior = flingBehavior
    ) { _, item ->
        itemContent(item)
    }
}

@Composable
fun <C : VMDIdentifiableContent> VMDLazyRowIndexed(
    modifier: Modifier = Modifier,
    viewModel: VMDListViewModel<C>,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal =
        if (!reverseLayout) Arrangement.Start else Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    itemContent: @Composable LazyItemScope.(index: Int, item: C) -> Unit
) {
    val listViewModel: VMDListViewModel<C> by viewModel.observeAsState()

    LazyRow(
        modifier = Modifier
            .hidden(listViewModel.isHidden)
            .then(modifier),
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        flingBehavior = flingBehavior
    ) {
        itemsIndexed(listViewModel.elements, key = { _, item -> item.identifier }, itemContent = itemContent)
    }
}
