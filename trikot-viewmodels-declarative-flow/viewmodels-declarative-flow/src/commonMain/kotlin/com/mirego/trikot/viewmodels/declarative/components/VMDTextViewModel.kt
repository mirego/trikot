package com.mirego.trikot.viewmodels.declarative.components

import com.mirego.trikot.viewmodels.declarative.properties.VMDRichTextRange
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel

interface VMDTextViewModel : VMDViewModel {
    val text: String
    val ranges: List<VMDRichTextRange>
}
