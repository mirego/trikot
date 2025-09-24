import SwiftUI
import Jasper
import UIKit

public struct VMDHtmlText: View {
    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDHtmlTextViewModel>
    
    private let style: VMDHtmlTextStyle
    private let color: SwiftUI.Color
    private let numberOfLines: Int
    private let linkTextAttributes: [NSAttributedString.Key : Any]?
    
    public init(
        _ viewModel: VMDHtmlTextViewModel,
        style: VMDHtmlTextStyle,
        color: SwiftUI.Color,
        numberOfLines: Int = 0,
        linkTextAttributes: [NSAttributedString.Key : Any]? = nil
    ) {
        self.observableViewModel = viewModel.asObservable()
        self.style = style
        self.color = color
        self.numberOfLines = numberOfLines
        self.linkTextAttributes = linkTextAttributes
    }
    
    private var viewModel: VMDHtmlTextViewModel {
        observableViewModel.viewModel
    }
    
    public var body: some View {
        HTMLText(
            html: viewModel.html,
            style: style,
            color: color,
            numberOfLines: numberOfLines,
            linkTextAttributes: linkTextAttributes,
            onOpenURL: viewModel.urlActionBlock
        )
    }
}

private struct HTMLText: UIViewRepresentable {

    private let html: String
    private let style: VMDHtmlTextStyle
    private let color: SwiftUI.Color
    private let numberOfLines: Int
    private let linkTextAttributes: [NSAttributedString.Key : Any]?
    private let onOpenURL: ((String) -> Void)?
    
    init(
        html: String,
        style: VMDHtmlTextStyle,
        color: SwiftUI.Color,
        numberOfLines: Int = 0,
        linkTextAttributes: [NSAttributedString.Key : Any]? = nil,
        onOpenURL: ((String) -> Void)? = nil
    ) {
        self.html = html
        self.style = style
        self.color = color
        self.numberOfLines = numberOfLines
        self.linkTextAttributes = linkTextAttributes
        self.onOpenURL = onOpenURL
    }
    
    func makeUIView(context: Context) -> UITextView {
        let view = ContentTextView()
        view.isEditable = true
        view.isScrollEnabled = false
        view.backgroundColor = .clear
        view.textContainer.lineFragmentPadding = .zero
        view.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
        view.textContainer.lineBreakMode = .byTruncatingTail
        view.textContainer.maximumNumberOfLines = numberOfLines
        let isUserInteractionEnabled = onOpenURL != nil
        view.isUserInteractionEnabled = isUserInteractionEnabled
        if isUserInteractionEnabled {
            view.delegate = context.coordinator
        }
        return view
    }
    
    func updateUIView(_ textView: UITextView, context: Context) {
        DispatchQueue.main.async {
            textView.attributedText = format(string: fullHtml)
            textView.textColor = UIColor(color)
            if let linkTextAttributes {
                textView.linkTextAttributes = linkTextAttributes
            }
            textView.textContainer.maximumNumberOfLines = numberOfLines
            textView.accessibilityTraits = .staticText
        }
        textView.invalidateIntrinsicContentSize()
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    private func format(string: String) -> NSAttributedString? {
        guard let data = string.data(using: .utf8) else { return nil }
        
        let options: [NSAttributedString.DocumentReadingOptionKey: Any] = [
            .documentType: NSAttributedString.DocumentType.html,
            .characterEncoding: String.Encoding.utf8.rawValue
        ]
        return try? NSAttributedString(data: data, options: options, documentAttributes: nil)
    }
    
    private var fullHtml: String {
        """
        <html>
        <head>
            <style>
                body {
                    font-family: '\(style.fontFamily)';
                    font-size: \(style.fontSize);
                    font-weight: \(style.fontWeight.rawValue);
                    -webkit-text-size-adjust: none;
                    margin: 0;
                }
            </style>
        </head>
        <body>
            \(html)
        </body>
        </html>
        """
    }
    
    final class Coordinator: NSObject, UITextViewDelegate {
        let parent: HTMLText
        
        init(_ parent: HTMLText) {
            self.parent = parent
        }
        
        func textView(_ textView: UITextView, shouldInteractWith URL: URL, in characterRange: NSRange, interaction: UITextItemInteraction) -> Bool {
            parent.onOpenURL?(URL.absoluteString)
            return false
        }
    }
    
    private var fontSize: CGFloat {
        UIFontMetrics(forTextStyle: style.relativeTextStyle.uiFontTextStyle).scaledValue(for: style.fontSize)
    }
    
    private class ContentTextView: UITextView {
        override var canBecomeFirstResponder: Bool { false }
        
        override var intrinsicContentSize: CGSize {
            let sizeThatFits = sizeThatFits(CGSize(width: frame.width, height: .greatestFiniteMagnitude))
            return frame.height > 0 ? sizeThatFits : super.intrinsicContentSize
        }
    }
}

public struct VMDHtmlTextStyle {
    public var fontSize: CGFloat
    public var lineHeight: CGFloat
    public var fontFamily: String
    public var fontWeight: VMDHtmlTextWeight
    public var relativeTextStyle: SwiftUI.Font.TextStyle
    
    public init(
        fontSize: CGFloat,
        lineHeight: CGFloat,
        fontFamily: String = "-apple-system",
        fontWeight: VMDHtmlTextWeight = VMDHtmlTextWeight.normal,
        relativeTextStyle: SwiftUI.Font.TextStyle = .body
    ) {
        self.fontSize = fontSize
        self.lineHeight = lineHeight
        self.fontFamily = fontFamily
        self.fontWeight = fontWeight
        self.relativeTextStyle = relativeTextStyle
    }
}

public enum VMDHtmlTextWeight: Int {
    case thin = 100
    case extralight = 200
    case light = 300
    case normal = 400
    case medium = 500
    case semibold = 600
    case bold = 700
    case extrabold = 800
    case black = 900

    public static let w100 = VMDHtmlTextWeight.thin
    public static let w200 = VMDHtmlTextWeight.extralight
    public static let w300 = VMDHtmlTextWeight.light
    public static let w400 = VMDHtmlTextWeight.normal
    public static let w500 = VMDHtmlTextWeight.medium
    public static let w600 = VMDHtmlTextWeight.semibold
    public static let w700 = VMDHtmlTextWeight.bold
    public static let w800 = VMDHtmlTextWeight.extrabold
    public static let w900 = VMDHtmlTextWeight.black
}

private extension SwiftUI.Font.TextStyle {
    var uiFontTextStyle: UIFont.TextStyle {
        switch self {
        case .largeTitle: return .largeTitle
        case .title: return .title1
        case .title2: return .title2
        case .title3: return .title3
        case .headline: return .headline
        case .subheadline: return .subheadline
        case .body: return .body
        case .callout: return .callout
        case .caption: return .caption1
        case .caption2: return .caption2
        case .footnote: return .footnote
        @unknown default:
            return .body
        }
    }
}
