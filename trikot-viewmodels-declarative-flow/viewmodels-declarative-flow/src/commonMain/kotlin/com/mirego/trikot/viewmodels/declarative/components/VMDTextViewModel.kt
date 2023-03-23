package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.VMDRichTextSpan
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface VMDTextViewModel : VMDViewModel {
    val text: String
    val spans: List<VMDRichTextSpan>
}
