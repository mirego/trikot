import Trikot_viewmodels_declarative
import TrikotViewmodelsDeclarativeSample

class BaseViewModelViewController<VMC: VMDViewModelController<VM, N>, VM, V: RootViewModelView, N: VMDNavigationDelegate>: ViewModelViewController<VMC, VM, V, N> where VM == V.VM {
    var viewControllerFactory: ViewControllerFactory!
}
