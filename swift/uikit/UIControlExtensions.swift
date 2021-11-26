import TRIKOT_FRAMEWORK_NAME
import UIKit

extension ViewModelDeclarativeWrapper where Base : UIControl {
    public var controlViewModel: VMDControlViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UIControl {
    func bindViewModel(_ viewModel: VMDControlViewModel?) {
        if let controlViewModel = viewModel {
            observe(controlViewModel.publisher(for: \VMDControlViewModel.enabled)) { [weak self] enabled in
                self?.isEnabled = enabled
            }
        }
    }
}
