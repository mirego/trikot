import TRIKOT_FRAMEWORK_NAME
import UIKit

extension UIControl: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UIControl {
    public var controlViewModel: ControlViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UIControl {
    func bindViewModel(_ viewModel: ControlViewModel?) {
        if let controlViewModel = viewModel {
            observe(controlViewModel.publisher(for: \ControlViewModel.enabled)) { [weak self] enabled in
                self?.isEnabled = enabled
            }
        }
    }
}
