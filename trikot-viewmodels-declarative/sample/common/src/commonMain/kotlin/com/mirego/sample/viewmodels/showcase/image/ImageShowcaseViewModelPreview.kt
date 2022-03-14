package com.mirego.sample.viewmodels.showcase.image

import com.mirego.sample.resources.SampleImageResource
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl

class ImageShowcaseViewModelPreview : VMDViewModelImpl(CancellableManager()), ImageShowcaseViewModel {
    override val title = VMDComponents.Text.withContent("Image showcase", cancellableManager)
    override val closeButton = VMDComponents.Button.withImage(SampleImageResource.ICON_CLOSE, cancellableManager)

    override val localImageTitle = VMDComponents.Text.withContent("Local image", cancellableManager)
    override val localImage = VMDComponents.Image.local(SampleImageResource.IMAGE_BRIDGE, cancellableManager)

    override val remoteImageTitle = VMDComponents.Text.withContent("Remote image", cancellableManager)
    override val remoteImage = VMDComponents.Image.remote(
        "https://picsum.photos/2000/1600",
        SampleImageResource.IMAGE_PLACEHOLDER,
        cancellableManager
    )

    override val localImageDescriptorTitle = VMDComponents.Text.withContent("Local image descriptor", cancellableManager)
    override val localImageDescriptor = VMDImageDescriptor.Local(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageDescriptorTitle = VMDComponents.Text.withContent("Remote image descriptor", cancellableManager)
    override val remoteImageDescriptor = VMDImageDescriptor.Remote("https://picsum.photos/2000/1600", placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderImageTitle = VMDComponents.Text.withContent("Placeholder image", cancellableManager)
    override val placeholderImage = VMDComponents.Image.remote(null, SampleImageResource.IMAGE_PLACEHOLDER, cancellableManager)

    override val complexPlaceholderImageTitle = VMDComponents.Text.withContent("Complex placeholder image", cancellableManager)
    override val complexPlaceholderImage = VMDComponents.Image.remote(null, SampleImageResource.IMAGE_PLACEHOLDER, cancellableManager)
}
