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

                observe(metaInputText.userInput) {[weak self] (text: String) in
                    if self?.text != text {
                        self?.text = text
                    }
                }

                observe(metaInputText.placeholderText) {[weak self] (placeholder: String) in
                    self?.placeholder = placeholder
                }

                observe(metaInputText.inputType) {[weak self] (inputType: MetaInputType) in
                    switch inputType {
                    case MetaInputType.email:
                        self?.autocapitalizationType = .none
                        self?.keyboardType = .emailAddress
                        self?.isSecureTextEntry = false
                    case MetaInputType.text:
                        self?.keyboardType = .default
                        self?.isSecureTextEntry = false
                    case MetaInputType.password:
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
