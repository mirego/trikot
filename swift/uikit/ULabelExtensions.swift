import TRIKOT_FRAMEWORK_NAME
import UIKit

extension UILabel: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UILabel {
    public var textViewModel: TextViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UILabel {
    func bindViewModel(_ viewModel: TextViewModel?) {
        if let textViewModel = viewModel {
            observe(textViewModel.publisher(for: \TextViewModel.text)) { [weak self] text in
                self?.text = text
            }
        }
    }
}
