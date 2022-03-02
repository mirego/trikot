import UIKit
import Trikot
import TRIKOT_FRAMEWORK_NAME

class HomeViewController: BaseViewModelViewController<HomeViewModelController, HomeViewModel, HomeView, HomeNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension HomeViewController: HomeNavigationDelegate {
    func navigateToTextShowcase() {
        present(viewControllerFactory.textShowcase(), animated: true, completion: nil)
    }

    func navigateToButtonShowcase() {
        present(viewControllerFactory.buttonShowcase(), animated: true, completion: nil)
    }

    func navigateToImageShowcase() {
        //TODO:
    }

    func navigateToProgressShowcase() {
        //TODO:
    }

    func navigateToTextFieldShowcase() {
        //TODO:
    }

    func navigateToToggleShowcase() {
        //TODO:
    }
}
