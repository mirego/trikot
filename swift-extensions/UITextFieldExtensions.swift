import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UITextField {
    public var inputTextViewModel: InputTextViewModel? {
        get { return trikotViewModel() }
        set(value) {
            removeTarget(self, action: #selector(onEditingChanged), for: .editingChanged)
            viewModel = value
            if let inputTextViewModel = value {
                addTarget(self, action: #selector(onEditingChanged), for: .editingChanged)

                bindColor(inputTextViewModel.textColor, \UITextField.textColor)

                observe(inputTextViewModel.userInput) {[weak self] (text: String) in
                    if self?.text != text {
                        self?.text = text
                    }
                }

                observe(inputTextViewModel.placeholderText) {[weak self] (placeholder: String) in
                    self?.placeholder = placeholder
                }

                observe(inputTextViewModel.inputType) {[weak self] (inputType: InputTextType) in
                    switch inputType {
                    case InputTextType.email:
                        self?.autocapitalizationType = .none
                        self?.keyboardType = .emailAddress
                        self?.isSecureTextEntry = false
                    case InputTextType.text:
                        self?.keyboardType = .default
                        self?.isSecureTextEntry = false
                    case InputTextType.password:
                        self?.keyboardType = .default
                        self?.isSecureTextEntry = true
                    case InputTextType.phoneNumber:
                        self?.keyboardType = .phonePad
                        self?.isSecureTextEntry = false
                    case InputTextType.number:
                        self?.keyboardType = .numberPad
                        self?.isSecureTextEntry = false
                    case InputTextType.multiline:
                        self?.keyboardType = .default
                        self?.isSecureTextEntry = false
                    default:
                        break
                    }
                }
            }
        }
    }

    @objc
    private func onEditingChanged() {
        inputTextViewModel?.setUserInput(value: text ?? "")
    }
}
