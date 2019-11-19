package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.mutable.MutableMetaInputText
import com.mirego.trikot.metaviews.mutable.MutableMetaView

class MutableMetaInputTextListItem(override var comparableId: String = ""): MetaInputTextListItem, MutableMetaView() {
    override val inputText = MutableMetaInputText()

    override fun isTheSame(other: MetaListItem): Boolean {
        return false
    }
}
