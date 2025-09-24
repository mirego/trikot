import Jasper
import Trikot
import UIKit

extension UIView: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UIView {
    public var viewModel: VMDViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            base.unsubscribeFromAllPublisher()
            base.vmd.setViewModel(viewModel: value)
            base.bindViewModel(viewModel)
        }
    }
}

fileprivate extension UIView {
    func bindViewModel(_ viewModel: VMDViewModel?) {
        guard let viewModel = viewModel else { return }
        observe(viewModel.publisher(for: \VMDViewModel.isHidden)) { [weak self] hidden in
            self?.isHidden = hidden
        }
    }
}
