package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.MetaView

interface MetaListItem : MetaView {
    var comparableId: String
    fun isTheSame(other: MetaListItem): Boolean {
        return comparableId == other.comparableId
    }
    fun haveTheSameContent(other: MetaListItem): Boolean {
        return true
    }
}
