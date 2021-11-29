import UIKit
import Trikot_viewmodels_declarative
import TrikotViewmodelsDeclarativeSample

class HomeViewController: ViewModelViewController<HomeViewModelController, HomeViewModel, HomeView, HomeNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension HomeViewController: HomeNavigationDelegate {
    func navigateToButtonShowcase() {
        //TODO:
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

    func navigateToTextShowcase() {
        //TODO:
    }

    func navigateToToggleShowcase() {
        //TODO: 
    }


}
