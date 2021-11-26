import TRIKOT_FRAMEWORK_NAME
import UIKit

extension ViewModelDeclarativeWrapper where Base : UISwitch {
    public var toggleViewModel: VMDToggleViewModel<VMDNoContent>? {
        get { return base.vmd.getViewModel() }
        set(value) {
            controlViewModel = value
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UISwitch {
    func bindViewModel(_ viewModel: VMDToggleViewModel<VMDNoContent>?) {
        removeTarget(self, action: #selector(UISwitch.onValueChanged), for: .valueChanged)
        if let toggleViewModel = viewModel {
            observe(toggleViewModel.publisher(for: \VMDToggleViewModel<VMDNoContent>.isOn)) { [weak self] isOn in
                self?.isOn = isOn
            }
            self.addTarget(self, action: #selector(UISwitch.onValueChanged), for: .valueChanged)
        }
    }

    @objc
    private func onValueChanged(sender: UISwitch) {
        vmd.toggleViewModel?.onValueChange(isOn: isOn)
    }
}
