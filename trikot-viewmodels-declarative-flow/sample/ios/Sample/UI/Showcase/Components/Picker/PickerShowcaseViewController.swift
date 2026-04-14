import UIKit
import Trikot
import SampleTrikotFrameworkName

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
