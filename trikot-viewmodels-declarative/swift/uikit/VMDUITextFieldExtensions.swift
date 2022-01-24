import TRIKOT_FRAMEWORK_NAME
import UIKit

extension ViewModelDeclarativeWrapper where Base : UITextField {
    public var textFieldViewModel: VMDTextFieldViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UITextField {
    func bindViewModel(_ textFieldViewModel: VMDTextFieldViewModel?) {
        removeTarget(self, action: #selector(vmd_editingChanged), for: .editingChanged)
        if let textFieldViewModel = textFieldViewModel {
            addTarget(self, action: #selector(vmd_editingChanged), for: .editingChanged)
            vmd.observe(textFieldViewModel.publisher(for: \VMDTextFieldViewModel.text)) { [weak self] text in
                guard let self = self else { return }
                if self.text != text {
                    self.text = text
                }
            }

            vmd.observe(textFieldViewModel.publisher(for: \VMDTextFieldViewModel.placeholder)) { [weak self] placeholder in
                self?.placeholder = placeholder
            }

            vmd.observe(textFieldViewModel.publisher(for: \VMDTextFieldViewModel.keyboardType)) { [weak self] keyboardType in
                self?.keyboardType = keyboardType.uiKeyboardType
            }

            vmd.observe(textFieldViewModel.publisher(for: \VMDTextFieldViewModel.keyboardReturnKeyType)) { [weak self] keyboardReturnKeyType in
                self?.returnKeyType = keyboardReturnKeyType.uiReturnKeyType
            }

            vmd.observe(textFieldViewModel.publisher(for: \VMDTextFieldViewModel.contentType)) { [weak self] contentType in
                self?.textContentType = contentType?.uiTextContentType
            }

            vmd.observe(textFieldViewModel.publisher(for: \VMDTextFieldViewModel.autoCorrect)) { [weak self] autoCorrect in
                if autoCorrect {
                    self?.autocorrectionType = .yes
                } else {
                    self?.autocorrectionType = .no
                }
            }

            vmd.observe(textFieldViewModel.publisher(for: \VMDTextFieldViewModel.autoCapitalization)) { [weak self] keyboardAutoCapitalization in
                self?.autocapitalizationType = keyboardAutoCapitalization.uiTextAutocapitalizationType
            }
        }
    }

    @objc
    private func vmd_editingChanged() {
        guard let textFieldViewModel = vmd.textFieldViewModel else { return }
        textFieldViewModel.onValueChange(text: textFieldViewModel.formatText(text ?? ""))
    }
}
