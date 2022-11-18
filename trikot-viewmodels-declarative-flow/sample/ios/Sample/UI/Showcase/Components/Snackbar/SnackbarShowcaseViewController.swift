import UIKit
import Trikot
import TRIKOT_FRAMEWORK_NAME

class SnackbarShowcaseViewController: BaseViewModelViewController<SnackbarShowcaseViewModelController, SnackbarShowcaseViewModel, SnackbarShowcaseView, SnackbarShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension SnackbarShowcaseViewController: SnackbarShowcaseNavigationDelegate {
    func showMessage(text: String) {
        let alertController = UIAlertController(title: nil, message: text, preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
        present(alertController, animated: true, completion: nil)
    }

    func close() {
        dismiss(animated: true, completion: nil)
    }
}
