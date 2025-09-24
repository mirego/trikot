import UIKit
import Trikot
import Jasper

class TextFieldShowcaseViewController: BaseViewModelViewController<TextFieldShowcaseViewModelController, TextFieldShowcaseViewModel, TextFieldShowcaseView, TextFieldShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension TextFieldShowcaseViewController: TextFieldShowcaseNavigationDelegate {
    func close() {
        dismiss(animated: true, completion: nil)
    }
}
