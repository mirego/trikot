package com.mirego.sample.factories

class Bootstrap {
    val viewModelControllerFactory: SampleViewModelControllerFactory = SampleViewModelControllerFactoryImpl()

    companion object {
        val shared = Bootstrap()
    }
}
