import UIKit
import Trikot
import SampleTrikotFrameworkName

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
