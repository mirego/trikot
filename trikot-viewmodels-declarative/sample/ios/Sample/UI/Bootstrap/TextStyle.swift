import SwiftUI
import Trikot_viewmodels_declarative

enum TextStyle {
    case caption2
    case caption1
    case footnote
    case subheadline
    case button
    case callout
    case body
    case headline
    case title3
    case title2
    case title1
    case largeTitle
}

extension TextStyle {
    var font: Font {
        switch self {
        case .caption2:
            return Font.custom("AvenirNext-Regular", size: 11, relativeTo: .caption2)
        case .caption1:
            return Font.custom("AvenirNext-Regular", size: 12, relativeTo: .caption)
        case .footnote:
            return Font.custom("AvenirNext-Regular", size: 13, relativeTo: .footnote)
        case .subheadline:
            return Font.custom("AvenirNext-Regular", size: 15, relativeTo: .subheadline)
        case .button:
            return Font.custom("AvenirNext-Regular", size: 16, relativeTo: .callout)
        case .callout:
            return Font.custom("AvenirNext-Regular", size: 16, relativeTo: .callout)
        case .body:
            return Font.custom("AvenirNext-Regular", size: 17, relativeTo: .body)
        case .headline:
            return Font.custom("AvenirNext-DemiBold", size: 17, relativeTo: .headline)
        case .title3:
            return Font.custom("AvenirNext-Medium", size: 20, relativeTo: .title3)
        case .title2:
            return Font.custom("AvenirNext-Regular", size: 22, relativeTo: .title2)
        case .title1:
            return Font.custom("AvenirNext-Regular", size: 28, relativeTo: .title)
        case .largeTitle:
            return Font.custom("AvenirNext-Bold", size: 34, relativeTo: .largeTitle)
        }
    }
}

extension Text {
    func style(_ textStyle: TextStyle) -> Self {
        font(textStyle.font)
    }
}

extension VMDText {
    func style(_ textStyle: TextStyle) -> VMDText {
        configure { $0.style(textStyle) }
    }
}

extension View {
    func textStyle(_ textStyle: TextStyle) -> some View {
        modifier(TextStyleModifier(textStyle: textStyle))
    }
}

fileprivate struct TextStyleModifier: ViewModifier {
    private let textStyle: TextStyle

    init(textStyle: TextStyle) {
        self.textStyle = textStyle
    }

    func body(content: Content) -> some View {
        content
            .font(textStyle.font)
    }
}
