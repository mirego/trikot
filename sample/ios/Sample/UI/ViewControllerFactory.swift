import UIKit
import TrikotViewmodelsDeclarativeSample
import Trikot_viewmodels_declarative

public class ViewControllerFactory {
    private let viewModelControllerFactory: SampleViewModelControllerFactory

    init(viewModelControllerFactory: SampleViewModelControllerFactory) {
        self.viewModelControllerFactory = viewModelControllerFactory
    }

    func home() -> HomeViewController {
        HomeViewController(viewModelController: viewModelControllerFactory.home())
    }
}
