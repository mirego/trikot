package com.trikot.sample.factories

import com.mirego.trikot.kword.I18N
import com.mirego.trikot.kword.KWord

class Bootstrap {
    val i18N: I18N = KWord

    val viewModelFactory: ViewModelFactory =
        ViewModelFactoryImpl(this)
}
