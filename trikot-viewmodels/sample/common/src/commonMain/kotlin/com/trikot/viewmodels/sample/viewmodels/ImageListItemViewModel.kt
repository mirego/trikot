package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.ImageViewModel
import com.mirego.trikot.viewmodels.ListItemViewModel

interface ImageListItemViewModel : ListItemViewModel {
    val image: ImageViewModel
}
