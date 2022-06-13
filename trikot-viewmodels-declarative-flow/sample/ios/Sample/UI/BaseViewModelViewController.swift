import Trikot
import TRIKOT_FRAMEWORK_NAME

class BaseViewModelViewController<VMC: VMDViewModelController<VM, N>, VM, V: RootViewModelView, N: VMDNavigationDelegate>: ViewModelViewController<VMC, VM, V, N> where VM == V.VM {
    var viewControllerFactory: ViewControllerFactory!
}
