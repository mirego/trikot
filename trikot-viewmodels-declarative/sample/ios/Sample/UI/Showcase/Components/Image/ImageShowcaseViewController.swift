import UIKit
import Trikot
import SampleTrikotFrameworkName

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
