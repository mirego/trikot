import TrikotFrameworkName
import UIKit

class BaseViewModelView<VM: ViewModel>: UIView {
    var viewViewModel: VM?

    override required init(frame: CGRect) {
        super.init(frame: frame)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
