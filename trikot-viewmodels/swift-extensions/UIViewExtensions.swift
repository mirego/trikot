import UIKit
import Jasper
import Trikot

extension UIView {
    public var trikotViewModel: ViewModel? {
        get { return getTrikotViewModel() }
        set(viewModel) {
            unsubscribeFromAllPublisher()
            setTrikotViewModel(viewModel: viewModel)
            removePreviousRegisteredTapAction()
            guard let viewModel = viewModel else { return }

            bind(viewModel.alpha, \UIView.alpha)

            bindColorSelectorDefaultValue(viewModel.backgroundColor, \UIView.backgroundColor)

            bind(viewModel.hidden, \UIView.isHidden)

            bind(viewModel.isAccessibilityElement, \UIView.isAccessibilityElement)

            bind(viewModel.accessibilityLabel, \UIView.accessibilityLabel)

            observe(viewModel.accessibilityHint) {[weak self] (value: ViewModelAccessibilityHint) in
                guard let self = self else { return }

                self.accessibilityHint = value.hint

                if value.announceHintChanges && self.accessibilityElementIsFocused() {
                    let hintToAnnounce = value.customHintsChangeAnnouncement ?? value.hint

                    /**
                     To prevent default VoiceOver from discarding the announcement,
                     we wait a short period of time before queuing our custom announcement.
                     We don't have to wait for the engine to finish talking, but we have
                     to make sure we don't queue our announcement before VoiceOver starts its own.
                     */
                    if UIAccessibility.isVoiceOverRunning {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
                            let delayedHintToAnnounce = NSAttributedString(string: hintToAnnounce, attributes: [.accessibilitySpeechQueueAnnouncement: true])
                            UIAccessibility.post(notification: .announcement, argument: delayedHintToAnnounce)
                        }
                    } else {
                        UIAccessibility.post(notification: .announcement, argument: hintToAnnounce)
                    }
                }
            }

            if !(self is UIControl) {
                observe(viewModel.action) {[weak self] (value: ViewModelAction) in
                    guard let self = self else { return }
                    self.removePreviousRegisteredTapAction()
                    if value != ViewModelAction.Companion().None {
                        let tapGestureReconizer = TrikotTapGestureRecognizer(target: self, action: #selector(self.trikotOnViewTouchUp))
                        self.addGestureRecognizer(tapGestureReconizer)
                    }
                }
            }
        }
    }

    private struct AssociatedKeys {
        static var viewModelKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    public func getTrikotViewModel<T>() -> T? {
        return objc_getAssociatedObject(self, AssociatedKeys.viewModelKey) as? T
    }

    public func setTrikotViewModel<T>(viewModel: T?) {
        objc_setAssociatedObject(self, AssociatedKeys.viewModelKey, viewModel, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }

    @objc
    private func trikotOnViewTouchUp() {
        let localViewModel: ViewModel? = getTrikotViewModel()
        guard let viewModelModel = localViewModel else { return }
        observe(viewModelModel.action.first()) {[weak self] (value: ViewModelAction) in value.execute(actionContext: self) }
    }

    public func richTextToAttributedString(_ richText: RichText, referenceFont: UIFont) -> NSAttributedString {
        let attributedString = NSMutableAttributedString(string: richText.text)
        richText.ranges.forEach { (richTextRange) in

            let range = NSRange(location: richTextRange.range.start.intValue, length: richTextRange.range.endInclusive.intValue - richTextRange.range.start.intValue)
            switch richTextRange.transform {
            case let transform as StyleTransform:
                switch transform.style {
                case .bold:
                    attributedString.addAttribute(.font, value: fontWithTrait(.traitBold, referenceFont: referenceFont), range: range)
                case .italic:
                    attributedString.addAttribute(.font, value: fontWithTrait(.traitItalic, referenceFont: referenceFont), range: range)
                case .boldItalic:
                    attributedString.addAttribute(.font, value: fontWithTrait([ .traitBold, .traitItalic ], referenceFont: referenceFont), range: range)
                case .underline:
                    attributedString.addAttribute(.underlineStyle, value: 1, range: range)
                case .normal:
                    break
                default:
                    fatalError("LabelViewModel RichText StyleTransform unsupported: \(transform.style)")
                }

            case let transform as ColorTransform:
                attributedString.addAttribute(.foregroundColor, value: transform.color.safeColor(), range: range)

            case let transform as TextAppearanceResourceTransform:
                let resource = transform.textAppearanceResource
                if let textAppearanceAttributes = TextAppearanceViewModelResourceManager.shared.textAppearance(fromResource: resource) {
                    attributedString.setAttributes(textAppearanceAttributes.attributes, range: range)
                }
            default:
                break
            }
        }

        return attributedString
    }

    public func fontWithTrait(_ trait: UIFontDescriptor.SymbolicTraits, referenceFont: UIFont) -> UIFont {
        if let fontDescriptor = referenceFont.fontDescriptor.withSymbolicTraits(trait) {
            return UIFont(descriptor: fontDescriptor, size: referenceFont.pointSize)
        } else {
            return referenceFont
        }
    }

    private func removePreviousRegisteredTapAction() {
        if let recognizersToRemove = self.gestureRecognizers?.filter { $0 is TrikotTapGestureRecognizer } {
            recognizersToRemove.forEach { self.removeGestureRecognizer($0) }
        }
    }
}

class TrikotTapGestureRecognizer: UITapGestureRecognizer {}
