import UIKit
import Trikot
import SampleTrikotFrameworkName

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
