import SwiftUI

struct PreviewView<Value: View>: View {
    private let dynamicTypeSizes: [ContentSizeCategory] = [.extraSmall, .extraLarge, .extraExtraExtraLarge]

    private let layout: PreviewLayout
    private let builder: () -> Value

    init(_ layout: PreviewLayout = PreviewLayout.device, builder: @escaping () -> Value) {
        self.layout = layout
        self.builder = builder
    }

    var body: some View {
        Group {
            builder()
                .previewLayout(self.layout)
                .previewPadding(self.layout)
                .previewDisplayName("Normal")

            ForEach(dynamicTypeSizes, id: \.self) { sizeCategory in
                self.builder()
                    .previewLayout(self.layout)
                    .previewPadding(self.layout)
                    .environment(\.accessibilityEnabled, true)
                    .environment(\.sizeCategory, sizeCategory)
                    .previewDisplayName("\(sizeCategory)")
            }
        }
    }
}
