import TrikotFrameworkName
import UIKit

class HomeViewController: BaseViewModelViewController<HomeView, BaseNavigationDelegate, HomeViewModel, HomeViewModelController> {
    init() {
        super.init(viewModelController: Core.shared.viewModelControllerFactory.createHome())
    }
}
