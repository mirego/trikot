package com.mirego.kword.sample

import com.mirego.trikot.kword.flow.FlowI18N

class ServiceLocatorImpl(i18N: FlowI18N) : ServiceLocator {
    override val textProvider = TextProvider(i18N)
}
