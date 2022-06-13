package com.mirego.sample.viewmodels.showcase.components.image

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor
import kotlinx.coroutines.CoroutineScope

class ImageShowcaseViewModelImpl(i18N: I18N, coroutineScope: CoroutineScope) : ShowcaseViewModelImpl(coroutineScope), ImageShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_TITLE], coroutineScope)

    override val localImageTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_TITLE], coroutineScope)
    override val localImage = VMDComponents.Image.local(SampleImageResource.IMAGE_BRIDGE, coroutineScope)

    override val remoteImageTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_TITLE], coroutineScope)
    override val remoteImage = VMDComponents.Image.remote(
        "https://picsum.photos/2000/1600",
        SampleImageResource.IMAGE_PLACEHOLDER,
        coroutineScope
    )

    override val localImageDescriptorTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_IMAGE_DESCRIPTOR_TITLE], coroutineScope)
    override val localImageDescriptor = VMDImageDescriptor.Local(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageDescriptorTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_IMAGE_DESCRIPTOR_TITLE], coroutineScope)
    override val remoteImageDescriptor = VMDImageDescriptor.Remote("https://picsum.photos/2000/1600", placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderImageTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_IMAGE_TITLE], coroutineScope)
    override val placeholderImage = VMDComponents.Image.remote(null, SampleImageResource.IMAGE_PLACEHOLDER, coroutineScope)

    override val complexPlaceholderImageTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_COMPLEX_PLACEHOLDER_IMAGE_TITLE], coroutineScope)
    override val complexPlaceholderImage = VMDComponents.Image.remote(null, SampleImageResource.IMAGE_PLACEHOLDER, coroutineScope)
}
