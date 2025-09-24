import SwiftUI
import Jasper

struct LifecycleModifier: ViewModifier {

    @Environment(\.scenePhase) var scenePhase

    let viewModel: VMDLifecycleViewModel

    @State private var skipFirstScenePhase = true
    @State private var isDisplayed = false

    func body(content: Content) -> some View {
        content
            .onChange(of: scenePhase) { newPhase in
                if skipFirstScenePhase {
                    skipFirstScenePhase = false
                } else {
                    if isDisplayed {
                        if newPhase == .active {
                            viewModel.onAppear()
                        } else if newPhase == .background {
                            viewModel.onDisappear()
                        }
                    }
                }
            }
            .onAppear {
                isDisplayed = true
                viewModel.onAppear()
            }
            .onDisappear {
                isDisplayed = false
                viewModel.onDisappear()
            }
    }
}

public extension View {
    func handleLifecycle(_ viewModel: VMDLifecycleViewModel) -> some View {
        modifier(LifecycleModifier(viewModel: viewModel))
    }
}

