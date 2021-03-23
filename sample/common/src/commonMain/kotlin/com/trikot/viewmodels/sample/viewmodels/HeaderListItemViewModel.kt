package com.trikot.viewmodels.sample.viewmodels

import com.mirego.trikot.viewmodels.LabelViewModel
import com.mirego.trikot.viewmodels.ListItemViewModel

interface HeaderListItemViewModel : ListItemViewModel {
    val text: LabelViewModel
}
