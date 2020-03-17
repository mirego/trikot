package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.mutable.MutableMetaView

class MutableMetaViewListItem(override var comparableId: String = "") : MetaViewListItem, MutableMetaView() {
    override val view = MutableMetaView()
}
