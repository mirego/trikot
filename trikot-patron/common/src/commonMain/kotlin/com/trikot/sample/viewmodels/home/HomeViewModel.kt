package com.trikot.sample.viewmodels.home

import com.mirego.trikot.viewmodels.ButtonViewModel
import com.mirego.trikot.viewmodels.LabelViewModel
import com.mirego.trikot.viewmodels.ViewModel

interface HomeViewModel : ViewModel {
    val quoteLabel: LabelViewModel
    val refreshButton: ButtonViewModel
}
