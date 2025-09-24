import UIKit
import Trikot
import Jasper

class AnimationTypesShowcaseViewController: BaseViewModelViewController<AnimationTypesShowcaseViewModelController, AnimationTypesShowcaseViewModel, AnimationTypesShowcaseView, AnimationTypesShowcaseNavigationDelegate> {
    override var preferredStatusBarStyle: UIStatusBarStyle {
        .darkContent
    }
}

extension AnimationTypesShowcaseViewController: AnimationTypesShowcaseNavigationDelegate {
    func close() {
        dismiss(animated: true, completion: nil)
    }
}
