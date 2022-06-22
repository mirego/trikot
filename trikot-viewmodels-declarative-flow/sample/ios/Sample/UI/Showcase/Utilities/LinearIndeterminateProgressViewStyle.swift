import SwiftUI

struct LinearIndeterminateProgressViewStyle: ProgressViewStyle {
    @State private var animating: Bool = false
    @Environment(\.isEnabled) private var isEnabled

    func makeBody(configuration: Self.Configuration) -> some View {
        Capsule()
            .foregroundColor(.gray.opacity(0.15))
            .overlay(
                GeometryReader { (geometry) in
                    Color.clear.background(
                        Capsule()
                            .clipShape(Capsule().offset(x: animating ? geometry.size.width * 0.95 : -geometry.size.width * 0.95, y: 0))
                            .foregroundColor(Color.accentColor)
                            .animation(.default.repeatForever(autoreverses: true).speed(0.265))
                            .onAppear{
                                DispatchQueue.main.async {
                                    animating.toggle()
                                }
                            }
                    )
                }
            )
            .opacity(self.isEnabled ? 1 : 0)
            .frame(height: 4)
    }
}

extension ProgressViewStyle where Self == LinearIndeterminateProgressViewStyle {
    static var linearIndeterminate: LinearIndeterminateProgressViewStyle {
        get {
            LinearIndeterminateProgressViewStyle()
        }
    }
}

struct IndeterminateLinearProgressViewStylePreviews: PreviewProvider {
    static var previews: some View {
        ProgressView()
            .progressViewStyle(.linearIndeterminate)
    }
}
