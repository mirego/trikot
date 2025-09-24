import UIKit
import Trikot
import Jasper

class ToggleShowcaseViewController: BaseViewModelViewController<ToggleShowcaseViewModelController, ToggleShowcaseViewModel, ToggleShowcaseView, ToggleShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension ToggleShowcaseViewController: ToggleShowcaseNavigationDelegate {
    func close() {
        dismiss(animated: true, completion: nil)
    }
}
