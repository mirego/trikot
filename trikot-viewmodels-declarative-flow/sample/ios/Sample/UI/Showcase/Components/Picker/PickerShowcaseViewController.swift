import UIKit
import Trikot
import TrikotFrameworkName

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
