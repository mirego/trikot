import UIKit
import Jasper

extension UITextView {
    public var trikotLabelViewModel: LabelViewModel? {
        get { getTrikotViewModel() }
        set(value) {
            trikotViewModel = value
            if let labelViewModel = value {
                if let richText = labelViewModel.richText, let font = self.font {
                    observe(richText) {[weak self] (richText: RichText) in
                        guard let self = self else { return }
                        self.attributedText = self.richTextToAttributedString(richText, referenceFont: font)
                    }
                } else {
                    bind(labelViewModel.text, \UITextView.text)
                }
                bindColorSelectorDefaultValue(labelViewModel.textColor, \UITextView.textColor)
            }
        }
    }
}
