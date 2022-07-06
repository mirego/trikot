import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UILabel {
    public var trikotLabelViewModel: LabelViewModel? {
        get { return getTrikotViewModel() }
        set(value) {
            trikotViewModel = value
            if let labelViewModel = value {
                if let richText = labelViewModel.richText {
                    observe(richText) {[weak self] (richText: RichText) in
                        guard let self = self else { return }
                        self.attributedText = self.richTextToAttributedString(richText, referenceFont: self.font)
                    }
                } else {
                    bind(labelViewModel.text, \UILabel.text)
                }

                bindColorSelectorDefaultValue(labelViewModel.textColor, \UILabel.textColor)
            }
        }
    }
}
