package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.mutable.MutableMetaLabel
import com.mirego.trikot.metaviews.mutable.MutableMetaView
import com.mirego.trikot.streams.reactive.just

class MutableHeaderListItem(text: String, override var comparableId: String = "") : MetaHeaderListItem, MutableMetaView() {
    override val text = MutableMetaLabel().also {
        it.text = text.just()
    }
}
