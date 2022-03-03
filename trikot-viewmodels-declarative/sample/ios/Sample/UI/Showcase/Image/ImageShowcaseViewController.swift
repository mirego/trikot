import UIKit
import Trikot
import TRIKOT_FRAMEWORK_NAME

class ImageShowcaseViewController: BaseViewModelViewController<ImageShowcaseViewModelController, ImageShowcaseViewModel, ImageShowcaseView, ImageShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension ImageShowcaseViewController: ImageShowcaseNavigationDelegate {
    func close() {
        dismiss(animated: true, completion: nil)
    }
}
