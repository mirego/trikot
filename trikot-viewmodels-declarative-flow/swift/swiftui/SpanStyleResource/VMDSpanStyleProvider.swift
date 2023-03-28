import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public typealias VMDSpanStyle = [NSAttributedString.Key: Any]

public protocol VMDSpanStyleProvider {
    func spanStyleForResource(textStyleResource: VMDTextStyleResource) -> VMDSpanStyle?
}

@available(iOS 15, *)
internal extension AttributedString {
    init(text: String, spans: [VMDRichTextSpan]) {
        let attributedString = NSMutableAttributedString(string: text)
        for span in spans {
            guard
                let transform = span.transform as? VMDSpanStyleResourceTransform,
                let spanStyle = TrikotViewModelDeclarative.shared.spanStyleProvider.spanStyleForResource(textStyleResource: transform.textStyleResource) else {
                continue
            }
            let start = Int(truncating: span.range.start)
            let length = Int(truncating: span.range.endInclusive) - start
            let range = NSRange(location: start, length: length)
            attributedString.addAttributes(spanStyle, range: range)
        }
        self.init(attributedString)
    }
}
