import TRIKOT_FRAMEWORK_NAME
import Trikot_streams
import UIKit

extension UIView: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UIView {
    public var viewModel: ViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            base.unsubscribeFromAllPublisher()
            base.vmd.setViewModel(viewModel: value)
            base.bindViewModel(viewModel)
        }
    }
}

fileprivate extension UIView {
    func bindViewModel(_ viewModel: ViewModel?) {
        guard let viewModel = viewModel else { return }
        observe(viewModel.publisher(for: \ViewModel.hidden)) { [weak self] hidden in
            self?.isHidden = hidden
        }
    }
}
