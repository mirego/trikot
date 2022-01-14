import UIKit
import TRIKOT_FRAMEWORK_NAME
import Trikot

public class ViewControllerFactory {
    private let viewModelControllerFactory: SampleViewModelControllerFactory

    init(viewModelControllerFactory: SampleViewModelControllerFactory) {
        self.viewModelControllerFactory = viewModelControllerFactory
    }

    func home() -> HomeViewController {
        HomeViewController(viewModelController: viewModelControllerFactory.home()).assignFactory(self)
    }

    func textShowcase() -> TextShowcaseViewController {
        TextShowcaseViewController(viewModelController: viewModelControllerFactory.textShowcase()).assignFactory(self)
    }
}

private extension BaseViewModelViewController {
    func assignFactory(_ viewControllerFactory: ViewControllerFactory) -> Self {
        self.viewControllerFactory = viewControllerFactory
        return self
    }
}
