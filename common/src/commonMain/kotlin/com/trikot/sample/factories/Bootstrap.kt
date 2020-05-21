package com.trikot.sample.factories

import com.mirego.trikot.kword.KWord

class Bootstrap {
    val viewModelControllerFactory: ViewModelControllerFactory = ViewModelControllerFactoryImpl(KWord)

    companion object {
        val shared = Bootstrap()
    }
}
