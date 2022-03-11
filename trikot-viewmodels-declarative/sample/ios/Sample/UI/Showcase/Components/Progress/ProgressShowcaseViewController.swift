import UIKit
import Trikot
import TRIKOT_FRAMEWORK_NAME

class ProgressShowcaseViewController: BaseViewModelViewController<ProgressShowcaseViewModelController, ProgressShowcaseViewModel, ProgressShowcaseView, ProgressShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension ProgressShowcaseViewController: ProgressShowcaseNavigationDelegate {
    func close() {
        dismiss(animated: true, completion: nil)
    }
}
