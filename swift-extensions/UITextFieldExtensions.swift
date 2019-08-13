import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UITextField {
    public var metaInputText: MetaInputText? {
        get { return trikotMetaView() }
        set(value) {
            removeTarget(self, action: #selector(onEditingChanged), for: .editingChanged)
            metaView = value
            if let metaInputText = value {
                addTarget(self, action: #selector(onEditingChanged), for: .editingChanged)

                bindColorSelectorDefaultValue(metaInputText.textColor, \UITextField.textColor)

                observe(metaInputText.placeholderText) {[weak self] (placeholder: String) in
                    self?.placeholder = placeholder
                }

                observe(metaInputText.text) {[weak self] (text: String) in
                    self?.text = text
                    self?.metaInputText?.setUserInput(value: text)
                }

                observe(metaInputText.inputType) {[weak self] (inputType: InputType_) in
                    switch inputType {
                    case InputType_.email:
                        self?.autocapitalizationType = .none
                        self?.keyboardType = .emailAddress
                        self?.isSecureTextEntry = false
                    case InputType_.text:
                        self?.keyboardType = .default
                        self?.isSecureTextEntry = false
                    case InputType_.password:
                        self?.keyboardType = .default
                        self?.isSecureTextEntry = true
                    default:
                        break
                    }
                }
            }
        }
    }

    @objc
    private func onEditingChanged() {
        metaInputText?.setUserInput(value: text ?? "")
    }
}
