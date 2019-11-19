import UIKit
import TRIKOT_FRAMEWORK_NAME
import Trikot_streams

extension UIView {
    public var metaView: MetaView? {
        get { return trikotMetaView() }
        set(metaView) {
            unsubscribeFromAllPublisher()
            setTrikotMetaView(metaView: metaView)
            guard let metaView = metaView else { return }

            bind(metaView.alpha, \UIView.alpha)

            bindColorSelectorDefaultValue(metaView.backgroundColor, \UIView.backgroundColor)

            bind(metaView.hidden, \UIView.isHidden)

            let onTapResetableCancelableManager = CancellableManagerProvider()
            trikotInternalPublisherCancellableManager.add(cancellable: onTapResetableCancelableManager)

            if !(self is UIControl) {
                observe(metaView.onTap) {[weak self] (value: MetaAction) in
                    guard let self = self else { return }
                    let newCancellableManager = onTapResetableCancelableManager.cancelPreviousAndCreate()

                    if value != MetaAction.Companion().None {
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
        static var metaViewKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    public func trikotMetaView<T>() -> T? {
        return objc_getAssociatedObject(self, AssociatedKeys.metaViewKey) as? T
    }

    public func setTrikotMetaView<T>(metaView: T?) {
        objc_setAssociatedObject(self, AssociatedKeys.metaViewKey, metaView, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }

    @objc
    private func trikotOnViewTouchUp() {
        let localMetaView: MetaView? = trikotMetaView()
        guard let metaMetaView = localMetaView else { return }
        observe(metaMetaView.onTap.first()) {[weak self] (value: MetaAction) in value.execute(actionContext: self) }
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
                    fatalError("MetaLabel RichText StyleTransform unsupported: \(transform.style)")
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
