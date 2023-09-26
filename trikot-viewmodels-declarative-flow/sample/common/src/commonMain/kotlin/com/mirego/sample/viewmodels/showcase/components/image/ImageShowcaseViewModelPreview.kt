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

    override val placeholderNoImageTitle = text("Placeholder no image")
    override val placeholderNoImage = remoteImage(null, SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderInvalidImageTitle = text("Placeholder invalid image")
    override val placeholderInvalidImage = remoteImage("https://invalidimageimageurl.ca/no_image.jpeg", SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderLoadingImageTitle = text("Long loading image")
    override val placeholderLoadingImage =
        remoteImage(
            "https://www.nasa.gov/sites/default/files/thumbnails/image/main_image_star-forming_region_carina_nircam_final-5mb.jpg",
            SampleImageResource.IMAGE_PLACEHOLDER
        )
}
