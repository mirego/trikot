package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.MetaView

interface MetaViewListItem : MetaListItem {
    val view: MetaView
}
