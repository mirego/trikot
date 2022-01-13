import UIKit
import Trikot_viewmodels_declarative
import TRIKOT_FRAMEWORK_NAME

class TextShowcaseViewController: BaseViewModelViewController<TextShowcaseViewModelController, TextShowcaseViewModel, TextShowcaseView, TextShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension TextShowcaseViewController: TextShowcaseNavigationDelegate {
    func close() {
        dismiss(animated: true, completion: nil)
    }
}
