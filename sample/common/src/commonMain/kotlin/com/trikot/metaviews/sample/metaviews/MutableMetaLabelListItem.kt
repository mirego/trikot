package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.mutable.MutableMetaLabel
import com.mirego.trikot.metaviews.mutable.MutableMetaView

class MutableMetaLabelListItem(override var comparableId: String = "") : MetaLabelListItem, MutableMetaView() {
    override val label = MutableMetaLabel()
}
