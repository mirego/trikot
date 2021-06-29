package com.mirego.sample.viewmodels.preview

import com.mirego.trikot.viewmodels.declarative.components.TextViewModel

class TextViewModelPreview(override val text: String, override var hidden: Boolean = false) :
    ViewModelPreview(hidden), TextViewModel
