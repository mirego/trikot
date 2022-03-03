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

    func buttonShowcase() -> ButtonShowcaseViewController {
        ButtonShowcaseViewController(viewModelController: viewModelControllerFactory.buttonShowcase()).assignFactory(self)
    }

    func imageShowcase() -> ImageShowcaseViewController {
        ImageShowcaseViewController(viewModelController: viewModelControllerFactory.imageShowcase()).assignFactory(self)
    }

    func toggleShowcase() -> ToggleShowcaseViewController {
        ToggleShowcaseViewController(viewModelController: viewModelControllerFactory.toggleShowcase()).assignFactory(self)
    }
}

private extension BaseViewModelViewController {
    func assignFactory(_ viewControllerFactory: ViewControllerFactory) -> Self {
        self.viewControllerFactory = viewControllerFactory
        return self
    }
}
