import SwiftUI

struct CircularDeterminateProgressViewStyle: ProgressViewStyle {
    @Environment(\.isEnabled) private var isEnabled

    func makeBody(configuration: Configuration) -> some View {
        Circle()
            .stroke(.gray.opacity(0.15), lineWidth: 4)
            .frame(idealWidth: 30, idealHeight: 30)
            .fixedSize()
            .overlay(progressView(configuration))
            .opacity(self.isEnabled ? 1 : 0)
    }

    @ViewBuilder
    private func progressView(_ configuration: Configuration) -> some View {
        if let fractionCompleted = configuration.fractionCompleted {
            Circle()
                .trim(from: 0, to: CGFloat(fractionCompleted))
                .stroke(Color.accentColor, lineWidth: 4)
                .rotationEffect(Angle.degrees(-90))
        }
    }
}

extension ProgressViewStyle where Self == CircularDeterminateProgressViewStyle {
    static var circularDeterminate: CircularDeterminateProgressViewStyle {
        get {
            CircularDeterminateProgressViewStyle()
        }
    }
}

struct CircularDeterminateProgressViewStylePreviews: PreviewProvider {
    static var previews: some View {
        ProgressView(value: 0.25)
            .progressViewStyle(.circularDeterminate)
    }
}
