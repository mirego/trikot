package com.mirego.sample.viewmodels.showcase.components.image

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor

class ImageShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), ImageShowcaseViewModel {
    override val title = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_TITLE], cancellableManager)

    override val localImageTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_TITLE], cancellableManager)
    override val localImage = VMDComponents.Image.local(SampleImageResource.IMAGE_BRIDGE, cancellableManager)

    override val remoteImageTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_TITLE], cancellableManager)
    override val remoteImage = VMDComponents.Image.remote(
        "https://picsum.photos/2000/1600",
        SampleImageResource.IMAGE_PLACEHOLDER,
        cancellableManager
    )

    override val localImageDescriptorTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_IMAGE_DESCRIPTOR_TITLE], cancellableManager)
    override val localImageDescriptor = VMDImageDescriptor.Local(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageDescriptorTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_IMAGE_DESCRIPTOR_TITLE], cancellableManager)
    override val remoteImageDescriptor = VMDImageDescriptor.Remote("https://picsum.photos/2000/1600", placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER)

    override val placeholderImageTitle = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_PLACEHOLDER_IMAGE_TITLE], cancellableManager)
    override val placeholderImage = VMDComponents.Image.remote(null, SampleImageResource.IMAGE_PLACEHOLDER, cancellableManager)
}
