import TrikotFrameworkName
import UIKit

class HomeViewController: BaseViewModelViewController<HomeView, HomeViewModelController, HomeViewModel> {
    init() {
        super.init(viewModelController: Core.shared.viewModelControllerFactory.createHome())
    }
}
