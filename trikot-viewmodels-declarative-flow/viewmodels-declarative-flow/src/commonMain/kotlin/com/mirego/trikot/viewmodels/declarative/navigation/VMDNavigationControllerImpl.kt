package com.mirego.trikot.viewmodels.declarative.navigation

class VMDNavigationControllerImpl(
    private val platformNavigationController: VMDPlatformNavigationController,
    initialDestination: VMDNavigationDestination<*>
) : VMDNavigationController {

    private val destinationStack = ArrayDeque<VMDNavigationDestination<*>>()

    init {
        destinationStack.addLast(initialDestination)
    }

    override fun push(
        navigationItem: VMDNavigationDestination<*>,
        options: VMDPushOptions?
    ) {
        destinationStack.addLast(navigationItem)
        platformNavigationController.push(
            destination = navigationItem,
            options = options
        )
    }

    override fun pop(
        result: VMDNavigationResult?,
        options: VMDPopOptions?
    ) {
        val currentNavigationItem = destinationStack.removeLast()
        val newDestination = destinationStack.last()
        platformNavigationController.pop(
            destination = newDestination,
            options = options
        )
        result?.let {
            currentNavigationItem.internalResultCallback.invoke(it)
        }
    }
}
