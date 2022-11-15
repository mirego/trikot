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

    func textFieldShowcase() -> TextFieldShowcaseViewController {
        TextFieldShowcaseViewController(viewModelController: viewModelControllerFactory.textFieldShowcase()).assignFactory(self)
    }

    func progressShowcase() -> ProgressShowcaseViewController {
        ProgressShowcaseViewController(viewModelController: viewModelControllerFactory.progressShowcase()).assignFactory(self)
    }

    func animationTypesShowcase() -> AnimationTypesShowcaseViewController {
        AnimationTypesShowcaseViewController(viewModelController: viewModelControllerFactory.animationTypesShowcase()).assignFactory(self)
    }
    
    func pickerShowcase() -> PickerShowcaseViewController {
        PickerShowcaseViewController(viewModelController: viewModelControllerFactory.pickerShowcase()).assignFactory(self)
    }
}

private extension BaseViewModelViewController {
    func assignFactory(_ viewControllerFactory: ViewControllerFactory) -> Self {
        self.viewControllerFactory = viewControllerFactory
        return self
    }
}
