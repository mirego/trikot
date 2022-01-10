package com.trikot.sample.viewmodels.base

actual abstract class ViewModelController {
    protected actual open fun onCleared() {
        // NO-OP
    }
}
