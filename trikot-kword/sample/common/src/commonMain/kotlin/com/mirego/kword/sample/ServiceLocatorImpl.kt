package com.mirego.kword.sample

import com.mirego.trikot.kword.FlowI18N

class ServiceLocatorImpl(private val i18N: FlowI18N) : ServiceLocator {
    override val textProvider = TextProvider(i18N)
}
