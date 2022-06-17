import SwiftUI
import TRIKOT_FRAMEWORK_NAME

extension View {
    @ViewBuilder
    func handleLifecycle(_ viewModel: VMDLifecycleViewModel) -> some View {
        self
            .onAppear { viewModel.onAppear() }
            .onDisappear { viewModel.onDisappear() }
    }
}
