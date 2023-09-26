package com.mirego.sample.viewmodels.showcase.components.image

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import com.mirego.trikot.viewmodels.declarative.viewmodel.localImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.remoteImage
import com.mirego.trikot.viewmodels.declarative.viewmodel.text
import kotlinx.coroutines.CoroutineScope

class ImageShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ImageShowcaseViewModel {
    override val title = text(i18N[KWordTranslation.IMAGE_SHOWCASE_TITLE])

    override val localImageTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_TITLE])
    override val localImage = localImage(SampleImageResource.IMAGE_BRIDGE, contentDescription = i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_TITLE])

    override val remoteImageTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_TITLE])
    override val remoteImage = remoteImage("https://picsum.photos/2000/1600", SampleImageResource.IMAGE_PLACEHOLDER, contentDescription = i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_TITLE])

    override val localImageDescriptorTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_IMAGE_DESCRIPTOR_TITLE])
    override val localImageDescriptor = VMDImageDescriptor.Local(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageDescriptorTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_IMAGE_DESCRIPTOR_TITLE])
    override val remoteImageDescriptor = VMDImageDescriptor.Remote("https://picsum.photos/2000/1600", placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderImageTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_IMAGE_TITLE])
    override val placeholderImage = remoteImage(
        imageUrl = null,
        placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER,
        contentDescription = i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_IMAGE_TITLE]
    )

    override val placeholderNoImageTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_NO_IMAGE_TITLE])

    override val placeholderNoImage = remoteImage(
        imageUrl = null,
        placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER,
        contentDescription = i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_NO_IMAGE_TITLE]
    )

    override val placeholderInvalidImageTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_INVALID_IMAGE_TITLE])

    override val placeholderInvalidImage = remoteImage(
        imageUrl = "https://invalidimageimageurl.ca/no_image.jpeg",
        placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER,
        contentDescription = i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_INVALID_IMAGE_TITLE]
    )

    override val placeholderLoadingImageTitle = text(i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_LOADING_IMAGE_TITLE])

    override val placeholderLoadingImage = remoteImage(
        imageUrl = "https://www.nasa.gov/sites/default/files/thumbnails/image/main_image_star-forming_region_carina_nircam_final-5mb.jpg",
        placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER,
        contentDescription = i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_LOADING_IMAGE_TITLE]
    )
}
