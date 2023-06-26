package com.mirego.trikot.viewmodels.properties

import com.mirego.trikot.viewmodels.ImageFlow
import com.mirego.trikot.viewmodels.resource.TrikotImageResource
import org.reactivestreams.Publisher

open class SimpleImageFlow(
    override val imageResource: TrikotImageResource? = null,
    override val placeholderImageResource: TrikotImageResource? = null,
    override val tintColor: Color? = null,
    override val accessibilityText: String? = null,
    override val url: String? = null,
    override val onSuccess: Publisher<ImageFlow>? = null,
    override val onError: Publisher<ImageFlow>? = null
) : ImageFlow
