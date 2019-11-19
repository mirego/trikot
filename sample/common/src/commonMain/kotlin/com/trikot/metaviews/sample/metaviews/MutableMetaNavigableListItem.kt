package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.mutable.MutableMetaLabel
import com.mirego.trikot.metaviews.mutable.MutableMetaView

class MutableMetaNavigableListItem(override var comparableId: String = ""): MetaNavigableListItem, MutableMetaView() {
    override val title = MutableMetaLabel()
}
