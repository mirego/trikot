import TrikotFrameworkName
import UIKit

class BaseViewModelViewController<V: BaseViewModelView<VM>, VMC: BaseViewModelController, VM: ViewModel>: UIViewController {
    var mainView: V {
        view as! V
    }

    let viewModelController: VMC

    init(viewModelController: VMC) {
        self.viewModelController = viewModelController
        super.init(nibName: nil, bundle: nil)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func loadView() {
        view = V(frame: .zero)
        mainView.viewViewModel = (viewModelController.viewModel as! VM)
    }

    deinit {
        viewModelController.onCleared()
    }
}
