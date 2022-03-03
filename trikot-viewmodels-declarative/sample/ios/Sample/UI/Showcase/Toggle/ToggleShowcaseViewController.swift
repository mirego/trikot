import UIKit
import Trikot
import TRIKOT_FRAMEWORK_NAME

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
