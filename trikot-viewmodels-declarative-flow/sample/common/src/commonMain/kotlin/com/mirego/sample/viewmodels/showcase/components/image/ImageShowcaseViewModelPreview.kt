package com.mirego.sample.viewmodels.showcase.components.image

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import kotlinx.coroutines.MainScope

class ImageShowcaseViewModelPreview : VMDViewModelImpl(MainScope()), ImageShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Image showcase", coroutineScope)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, coroutineScope)

    override val localImageTitle = VMDComponents.Text.withContent("Local image", coroutineScope)
    override val localImage = VMDComponents.Image.local(SampleImageResource.IMAGE_BRIDGE, coroutineScope)

    override val remoteImageTitle = VMDComponents.Text.withContent("Remote image", coroutineScope)
    override val remoteImage = VMDComponents.Image.remote(
        "https://picsum.photos/2000/1600",
        SampleImageResource.IMAGE_PLACEHOLDER,
        coroutineScope
    )

    override val localImageDescriptorTitle = VMDComponents.Text.withContent("Local image descriptor", coroutineScope)
    override val localImageDescriptor = VMDImageDescriptor.Local(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageDescriptorTitle = VMDComponents.Text.withContent("Remote image descriptor", coroutineScope)
    override val remoteImageDescriptor = VMDImageDescriptor.Remote("https://picsum.photos/2000/1600", placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderImageTitle = VMDComponents.Text.withContent("Placeholder image", coroutineScope)
    override val placeholderImage = VMDComponents.Image.remote(null, SampleImageResource.IMAGE_PLACEHOLDER, coroutineScope)

    override val complexPlaceholderImageTitle = VMDComponents.Text.withContent("Complex placeholder image", coroutineScope)
    override val complexPlaceholderImage = VMDComponents.Image.remote(null, SampleImageResource.IMAGE_PLACEHOLDER, coroutineScope)
}
