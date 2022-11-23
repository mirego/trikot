package com.mirego.sample.viewmodels.showcase.components.image

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.buttonWithImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.localImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.remoteImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.MainScope

class ImageShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ImageShowcaseViewModel {
    override val title = text("Image showcase")
    override val closeButton = buttonWithImage(SampleImageResource.ICON_CLOSE)

    override val localImageTitle = text("Local image")
    override val localImage = localImage(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageTitle = text("Remote image")
    override val remoteImage = remoteImage("https://picsum.photos/2000/1600", SampleImageResource.IMAGE_PLACEHOLDER)

    override val localImageDescriptorTitle = text("Local image descriptor")
    override val localImageDescriptor = VMDImageDescriptor.Local(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageDescriptorTitle = text("Remote image descriptor")
    override val remoteImageDescriptor = VMDImageDescriptor.Remote("https://picsum.photos/2000/1600", placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderImageTitle = text("Placeholder image")
    override val placeholderImage = remoteImage(null, SampleImageResource.IMAGE_PLACEHOLDER)

    override val complexPlaceholderImageTitle = text("Complex placeholder image")
    override val complexPlaceholderImage = remoteImage(null, SampleImageResource.IMAGE_PLACEHOLDER)
}
