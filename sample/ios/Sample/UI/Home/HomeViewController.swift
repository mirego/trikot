import UIKit
import Trikot_viewmodels_declarative
import TrikotViewmodelsDeclarativeSample

class HomeViewController: ViewModelViewController<HomeViewModelController, HomeViewModel, HomeView, HomeNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension HomeViewController: HomeNavigationDelegate {

}
