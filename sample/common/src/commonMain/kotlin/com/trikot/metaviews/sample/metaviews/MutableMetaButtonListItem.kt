package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.mutable.MutableMetaButton
import com.mirego.trikot.metaviews.mutable.MutableMetaView

class MutableMetaButtonListItem(override var comparableId: String = "") : MetaButtonListItem, MutableMetaView() {
    override val button = MutableMetaButton()

    override fun isTheSame(other: MetaListItem): Boolean {
        return false
    }
}
