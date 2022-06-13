import SwiftUI

struct RoundedShadowButtonStyle: ButtonStyle {
    private let shadowEnabled: Bool
    private let displayShadowWhenDisabled: Bool
    private let backgroundColor: Color
    private let foregroundColor: Color
    private let shadowColor: Color
    private let verticalPadding: CGFloat
    private let horizontalPadding: CGFloat
    private let isEnabled: Bool
    private let isLoading: Bool

    init(shadowEnabled: Bool = true,
         displayShadowWhenDisabled: Bool = false,
         backgroundColor: Color = Color(UIColor.systemIndigo),
         foregroundColor: Color = .white,
         shadowColor: Color? = Color.black,
         verticalPadding: CGFloat = 16,
         horizontalPadding: CGFloat = 16,
         isEnabled: Bool = true,
         isLoading: Bool = false) {
        self.shadowEnabled = shadowEnabled
        self.displayShadowWhenDisabled = displayShadowWhenDisabled
        self.backgroundColor = backgroundColor
        self.foregroundColor = foregroundColor
        self.shadowColor = shadowColor ?? backgroundColor
        self.verticalPadding = verticalPadding
        self.horizontalPadding = horizontalPadding
        self.isEnabled = isEnabled
        self.isLoading = isLoading
    }

    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding(.horizontal, horizontalPadding)
            .padding(.vertical, verticalPadding)
            .textStyle(.button)
            .foregroundColor((isEnabled || isLoading) ? foregroundColor : Color.gray)
            .background(
                ((isEnabled || isLoading) ? backgroundColor : Color.gray)
                    .clipShape(RoundedRectangle(cornerRadius: 19, style: .continuous))
                    .shadow(color: shadowColor.opacity((!isEnabled && !displayShadowWhenDisabled) ? 0 : (shadowEnabled ? 0.15 : 0)), radius: configuration.isPressed ? 7 : 10, x: 0, y: configuration.isPressed ? 5 : 10)
            )
            .scaleEffect(configuration.isPressed ? 0.96: 1)
    }
}

extension ButtonStyle where Self == RoundedShadowButtonStyle {
    static var roundedShadow: RoundedShadowButtonStyle {
        get {
            RoundedShadowButtonStyle()
        }
    }
}
