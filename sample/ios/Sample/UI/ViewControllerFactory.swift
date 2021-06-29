import UIKit
import TrikotViewmodelsDeclarativeSample
import Trikot_viewmodels_declarative

public class ViewControllerFactory {
    private let viewModelControllerFactory: ViewModelControllerFactory_

    init(viewModelControllerFactory: ViewModelControllerFactory_) {
        self.viewModelControllerFactory = viewModelControllerFactory
    }

    func home() -> HomeViewController {
        HomeViewController(viewModelController: viewModelControllerFactory.home())
    }
}
