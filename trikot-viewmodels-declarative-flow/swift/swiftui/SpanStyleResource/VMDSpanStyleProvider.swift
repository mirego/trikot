import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public typealias VMDSpanStyle = [NSAttributedString.Key: Any]

public protocol VMDSpanStyleProvider {
    func spanStyleForResource(spanStyleResource: VMDSpanStyleResource) -> VMDSpanStyle?
}

@available(iOS 15, *)
internal extension AttributedString {
    init(text: String, spans: [VMDRichTextSpan]) {
        self.init(
            NSAttributedString(text: text, spans: spans)
        )
    }
}

internal extension NSAttributedString {
    convenience init(text: String, spans: [VMDRichTextSpan]) {
        let attributedString = NSMutableAttributedString(string: text)
        for span in spans {
            guard
                let transform = span.transform as? VMDSpanStyleResourceTransform,
                let spanStyle = TrikotViewModelDeclarative.shared.spanStyleProvider.spanStyleForResource(spanStyleResource: transform.spanStyleResource) else {
                continue
            }
            let start = Int(truncating: span.range.start)
            let length = Int(truncating: span.range.endInclusive) - start
            let range = NSRange(location: start, length: length)
            attributedString.addAttributes(spanStyle, range: range)
        }
        self.init(attributedString: attributedString)
    }
}
