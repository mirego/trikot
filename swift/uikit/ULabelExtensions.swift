import TRIKOT_FRAMEWORK_NAME
import UIKit

extension ViewModelDeclarativeWrapper where Base : UILabel {
    public var textViewModel: VMDTextViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UILabel {
    func bindViewModel(_ viewModel: VMDTextViewModel?) {
        if let textViewModel = viewModel {
            observe(textViewModel.publisher(for: \VMDTextViewModel.text)) { [weak self] text in
                self?.text = text
            }
        }
    }
}
