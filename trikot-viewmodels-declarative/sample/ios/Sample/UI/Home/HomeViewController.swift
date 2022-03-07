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
        present(viewControllerFactory.imageShowcase(), animated: true, completion: nil)
    }

    func navigateToToggleShowcase() {
        present(viewControllerFactory.toggleShowcase(), animated: true, completion: nil)
    }

    func navigateToTextFieldShowcase() {
        //TODO:
    }

    func navigateToProgressShowcase() {
        present(viewControllerFactory.progressShowcase(), animated: true, completion: nil)
    }
}
