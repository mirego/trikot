package com.mirego.sample.factories

class Bootstrap {
    val viewModelControllerFactory: ViewModelControllerFactory = ViewModelControllerFactoryImpl()

    companion object {
        val shared = Bootstrap()
    }
}
