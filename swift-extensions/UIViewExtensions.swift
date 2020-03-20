import UIKit
import TRIKOT_FRAMEWORK_NAME
import Trikot_streams

extension UIView {
    public var viewModel: ViewModel? {
        get { return trikotViewModel() }
        set(viewModel) {
            unsubscribeFromAllPublisher()
            setTrikotViewModel(viewModel: viewModel)
            guard let viewModel = viewModel else { return }

            bind(viewModel.alpha, \UIView.alpha)

            bindColorSelectorDefaultValue(viewModel.backgroundColor, \UIView.backgroundColor)

            bind(viewModel.hidden, \UIView.isHidden)

            let onTapResetableCancelableManager = CancellableManagerProvider()
            trikotInternalPublisherCancellableManager.add(cancellable: onTapResetableCancelableManager)

            if !(self is UIControl) {
                observe(viewModel.action) {[weak self] (value: ViewModelAction) in
                    guard let self = self else { return }
                    let newCancellableManager = onTapResetableCancelableManager.cancelPreviousAndCreate()

                    if value != ViewModelAction.Companion().None {
                        let tapGestureReconizer = UITapGestureRecognizer(target: self, action: #selector(self.trikotOnViewTouchUp))
                        self.addGestureRecognizer(tapGestureReconizer)
                        newCancellableManager.add {[weak self] in
                            self?.removeGestureRecognizer(tapGestureReconizer)
                        }
                    }
                }
            }
        }
    }

    private struct AssociatedKeys {
        static var viewModelKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    public func trikotViewModel<T>() -> T? {
        return objc_getAssociatedObject(self, AssociatedKeys.viewModelKey) as? T
    }

    public func setTrikotViewModel<T>(viewModel: T?) {
        objc_setAssociatedObject(self, AssociatedKeys.viewModelKey, viewModel, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }

    @objc
    private func trikotOnViewTouchUp() {
        let localViewModel: ViewModel? = trikotViewModel()
        guard let viewModelModel = localViewModel else { return }
        observe(viewModelModel.action.first()) {[weak self] (value: ViewModelAction) in value.execute(actionContext: self) }
    }

    func richTextToAttributedString(_ richText: RichText, referenceFont: UIFont) -> NSAttributedString {
        let attributedString = NSMutableAttributedString(string: richText.text)
        richText.ranges.forEach { (richTextRange) in

            let range = NSRange(location: richTextRange.range.start.intValue, length: richTextRange.range.endInclusive.intValue - richTextRange.range.start.intValue)
            if let transform = richTextRange.transform as? StyleTransform {
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
            }
        }
        return attributedString
    }

    private func fontWithTrait(_ trait: UIFontDescriptor.SymbolicTraits, referenceFont: UIFont) -> UIFont {
        if let fontDescriptor = referenceFont.fontDescriptor.withSymbolicTraits(trait) {
            return UIFont(descriptor: fontDescriptor, size: referenceFont.pointSize)
        } else {
            return referenceFont
        }
    }    
}
