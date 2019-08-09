import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UILabel {
    public var metaLabel: MetaLabel? {
        get { return trikotMetaView() }
        set(value) {
            metaView = value
            if let metaLabel = value {
                if let richText = metaLabel.richText {
                    observe(richText) {[weak self] in
                        self?.updateRichText($0)
                    }
                } else {
                    bind(metaLabel.text, \UILabel.text)
                }

                bindColorSelectorDefaultValue(metaLabel.textColor, \UILabel.textColor)
            }
        }
    }

    func updateRichText(_ richText: RichText) {
        let attributedString = NSMutableAttributedString(string: richText.text)
        richText.ranges.forEach { (richTextRange) in

            let range = NSRange(location: richTextRange.range.start.intValue, length: richTextRange.range.endInclusive.intValue - richTextRange.range.start.intValue)
            if let transform = richTextRange.transform as? StyleTransform {
                switch transform.style {
                case .bold:
                    attributedString.addAttribute(.font, value: fontWithTrait(.traitBold), range: range)
                case .italic:
                    attributedString.addAttribute(.font, value: fontWithTrait(.traitItalic), range: range)
                case .boldItalic:
                    attributedString.addAttribute(.font, value: fontWithTrait([ .traitBold, .traitItalic ]), range: range)
                case .underline:
                    attributedString.addAttribute(.underlineStyle, value: 1, range: range)
                case .normal:
                    break
                default:
                    fatalError("MetaLabel RichText StyleTransform unsupported: \(transform.style)")
                }
            }
        }
        attributedText = attributedString
    }

    private func fontWithTrait(_ trait: UIFontDescriptor.SymbolicTraits) -> UIFont {
        if let fontDescriptor = font.fontDescriptor.withSymbolicTraits(trait) {
            return UIFont(descriptor: fontDescriptor, size: font.pointSize)
        } else {
            return font
        }
    }
}
