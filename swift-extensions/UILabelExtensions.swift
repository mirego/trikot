import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UILabel {
    public var metaLabel: MetaLabel? {
        get { return trikotMetaView() }
        set(value) {
            metaView = value
            if let metaLabel = value {
                if let richText = metaLabel.richText {
                    observe(richText) {[weak self] (richText: RichText) in
                        guard let self = self else { return }
                        self.attributedText = self.richTextToAttributedString(richText, referenceFont: self.font)
                    }
                } else {
                    bind(metaLabel.text, \UILabel.text)
                }

                bindColorSelectorDefaultValue(metaLabel.textColor, \UILabel.textColor)
            }
        }
    }
}
