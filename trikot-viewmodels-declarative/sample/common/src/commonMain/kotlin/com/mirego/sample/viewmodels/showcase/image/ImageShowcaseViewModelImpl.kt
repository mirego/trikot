package com.mirego.sample.viewmodels.showcase.image

import com.mirego.sample.KWordTranslation
import com.mirego.sample.resources.SampleImageResource
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModelImpl
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.components.VMDImageViewModel
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.components.factory.VMDComponents
import com.mirego.trikot.viewmodels.declarative.properties.VMDImageDescriptor

class ImageShowcaseViewModelImpl(i18N: I18N, cancellableManager: CancellableManager) : ShowcaseViewModelImpl(cancellableManager), ImageShowcaseViewModel {
    override val title: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_TITLE], cancellableManager)

    override val localImageTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_TITLE], cancellableManager)

    override val remoteImageTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_TITLE], cancellableManager)

    override val localImageDescriptorTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_LOCAL_IMAGE_DESCRIPTOR_TITLE], cancellableManager)

    override val remoteImageDescriptorTitle: VMDTextViewModel = VMDComponents.Text.withContent(i18N[KWordTranslation.IMAGE_SHOWCASE_REMOTE_IMAGE_DESCRIPTOR_TITLE], cancellableManager)

    override val localImage: VMDImageViewModel = VMDComponents.Image.local(SampleImageResource.IMAGE_BRIDGE, cancellableManager)

    override val remoteImage: VMDImageViewModel = VMDComponents.Image.remote(
        "https://picsum.photos/2000/1600",
        SampleImageResource.IMAGE_PLACEHOLDER,
        cancellableManager
    )
    override val localImageDescriptor: VMDImageDescriptor = VMDImageDescriptor.Local(SampleImageResource.IMAGE_BRIDGE)

    override val remoteImageDescriptor: VMDImageDescriptor = VMDImageDescriptor.Remote("https://picsum.photos/2000/1600", placeholderImageResource = SampleImageResource.IMAGE_PLACEHOLDER)
}
