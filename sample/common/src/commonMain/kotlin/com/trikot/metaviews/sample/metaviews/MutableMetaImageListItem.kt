package com.trikot.metaviews.sample.metaviews

import com.mirego.trikot.metaviews.mutable.ImageFlowProvider
import com.mirego.trikot.metaviews.mutable.MutableMetaImage
import com.mirego.trikot.metaviews.mutable.MutableMetaView

class MutableMetaImageListItem(imageFlowProvider: ImageFlowProvider, override var comparableId: String = "") : MetaImageListItem, MutableMetaView() {
    override val image = MutableMetaImage(imageFlowProvider)
}
