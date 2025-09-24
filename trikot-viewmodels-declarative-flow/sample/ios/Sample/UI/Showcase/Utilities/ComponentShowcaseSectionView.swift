import SwiftUI
import Trikot
import Jasper

struct ComponentShowcaseSectionView<Content: View>: View {
    private let titleTextViewModel: VMDTextViewModel
    private let content: Content

    init(_ titleTextViewModel: VMDTextViewModel, @ViewBuilder content: () -> Content) {
        self.titleTextViewModel = titleTextViewModel
        self.content = content()
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            VMDText(titleTextViewModel)
                .style(.title2)

            content
        }
    }
}
