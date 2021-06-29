import TRIKOT_FRAMEWORK_NAME
import UIKit

extension UITextField: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UITextField {
    public var textFieldViewModel: TextFieldViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UITextField {
    func bindViewModel(_ textFieldViewModel: TextFieldViewModel?) {
        removeTarget(self, action: #selector(onEditingChanged), for: .editingChanged)
        if let textFieldViewModel = textFieldViewModel {
            addTarget(self, action: #selector(onEditingChanged), for: .editingChanged)
            vmd.observe(textFieldViewModel.publisher(for: \TextFieldViewModel.text)) { [weak self] text in
                guard let self = self else { return }
                if self.text != text {
                    self.text = text
                }
            }

            vmd.observe(textFieldViewModel.publisher(for: \TextFieldViewModel.placeholder)) { [weak self] placeholder in
                self?.placeholder = placeholder
            }

            vmd.observe(textFieldViewModel.publisher(for: \TextFieldViewModel.keyboardType)) { [weak self] keyboardType in
                self?.keyboardType = keyboardType.uiKeyboardType
            }

            vmd.observe(textFieldViewModel.publisher(for: \TextFieldViewModel.keyboardReturnKeyType)) { [weak self] keyboardReturnKeyType in
                self?.returnKeyType = keyboardReturnKeyType.uiReturnKeyType
            }

            vmd.observe(textFieldViewModel.publisher(for: \TextFieldViewModel.contentType)) { [weak self] contentType in
                self?.textContentType = contentType?.uiTextContentType
            }

            vmd.observe(textFieldViewModel.publisher(for: \TextFieldViewModel.autoCorrect)) { [weak self] autoCorrect in
                if autoCorrect {
                    self?.autocorrectionType = .yes
                } else {
                    self?.autocorrectionType = .no
                }
            }

            vmd.observe(textFieldViewModel.publisher(for: \TextFieldViewModel.autoCapitalization)) { [weak self] keyboardAutoCapitalization in
                self?.autocapitalizationType = keyboardAutoCapitalization.uiTextAutocapitalizationType
            }
        }
    }

    @objc
    private func onEditingChanged() {
        guard let textFieldViewModel = vmd.textFieldViewModel else { return }
        textFieldViewModel.onValueChange(text: textFieldViewModel.formatText(text ?? ""))
    }
}

extension KeyboardType {
    public var uiKeyboardType: UIKeyboardType {
        switch self {
        case .default_:
            return .default
        case .ascii:
            return .asciiCapable
        case .number:
            return .numberPad
        case .email:
            return .emailAddress
        case .password:
            return .default
        case .numberpassword:
            return .numberPad
        case .phone:
            return .phonePad
        case .url:
            return .URL
        default:
            return .default
        }
    }
}

extension KeyboardReturnKeyType {
    public var uiReturnKeyType: UIReturnKeyType {
        switch self {
        case .default_:
            return .default
        case .done:
            return .done
        case .go:
            return .go
        case .next:
            return .next
        case .search:
            return .search
        case .send:
            return .send
        default:
            return .default
        }
    }
}

extension TextContentType {
    public var uiTextContentType: UITextContentType? {
        switch self {
        case .name:
            return .name
        case .nameprefix:
            return .namePrefix
        case .givenname:
            return .givenName
        case .middlename:
            return .middleName
        case .familyname:
            return .familyName
        case .namesuffix:
            return .nameSuffix
        case .nickname:
            return .nickname
        case .jobtitle:
            return .jobTitle
        case .organizationname:
            return .organizationName
        case .location:
            return .location
        case .fullstreetaddress:
            return .fullStreetAddress
        case .streetaddressline1:
            return .streetAddressLine1
        case .streetaddressline2:
            return .streetAddressLine2
        case .addresscity:
            return .addressCity
        case .addressstate:
            return .addressState
        case .addresscityandstate:
            return .addressCityAndState
        case .sublocality:
            return .sublocality
        case .countryname:
            return .countryName
        case .postalcode:
            return .postalCode
        case .telephonenumber:
            return .telephoneNumber
        case .emailaddress:
            return .emailAddress
        case .url:
            return .URL
        case .creditcardnumber:
            return .creditCardNumber
        case .username:
            return .username
        case .password:
            return .password
        case .newpassword:
            return .newPassword
        case .onetimecode:
            return .oneTimeCode
        default:
            return nil
        }
    }
}

extension KeyboardAutoCapitalization {
    public var uiTextAutocapitalizationType: UITextAutocapitalizationType {
        switch self {
        case .none:
            return .none
        case .characters:
            return .allCharacters
        case .sentences:
            return .sentences
        case .words:
            return .words
        default:
            return .none
        }
    }
}
