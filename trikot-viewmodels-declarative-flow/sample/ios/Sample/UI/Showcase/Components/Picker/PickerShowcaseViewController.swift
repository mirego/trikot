import UIKit
import Trikot
import TRIKOT_FRAMEWORK_NAME

class PickerShowcaseViewController: BaseViewModelViewController<PickerShowcaseViewModelController, PickerShowcaseViewModel, PickerShowcaseView, PickerShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension PickerShowcaseViewController: PickerShowcaseNavigationDelegate {
    func close() {
        dismiss(animated: true, completion: nil)
    }
}
