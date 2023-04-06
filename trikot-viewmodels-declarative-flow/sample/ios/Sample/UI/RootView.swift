import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct RootView: View {
    
    private var viewModelFactory: SampleViewModelFactory
    
    @ObservedObject private var platformNavigationController: VMDPlatformNavigationControllerImpl
    
    init(platformNavigationController: VMDPlatformNavigationControllerImpl, viewModelFactory: SampleViewModelFactory) {
        self.viewModelFactory = viewModelFactory
        self.platformNavigationController = platformNavigationController
    }
    
    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(
                    destination: NavigationLazyView(HomeView(viewModel: viewModelFactory.create(navigationDestination: GeneratedEnumOfDestinations.home, rawInput: "") as! HomeViewModel)),
                    tag: GeneratedEnumOfDestinations.home.identifier,
                    selection: $platformNavigationController.currentDestinationIdentifier
                ) { EmptyView() }
                NavigationLink(
                    destination: NavigationLazyView(TextShowcaseView(viewModel: viewModelFactory.create(navigationDestination: GeneratedEnumOfDestinations.textshowcase, rawInput: platformNavigationController.currentDestination?.input?.serialized ?? "") as! TextShowcaseViewModel)),
                    tag: GeneratedEnumOfDestinations.textshowcase.identifier,
                    selection: $platformNavigationController.currentDestinationIdentifier
                ) { EmptyView() }
            }
            .sheet(isPresented: Binding(get: { platformNavigationController.currentModalIdentifier == GeneratedEnumOfDestinations.dialogshowcase.identifier }, set: { v in } )) {
                DialogView(viewModel: viewModelFactory.create(navigationDestination: GeneratedEnumOfDestinations.dialogshowcase, rawInput: platformNavigationController.currentModalDestination?.input?.serialized ?? "") as! DialogViewModel)
            }
        }
    }
}

struct NavigationLazyView<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}

extension View {
    func log(_ value: Any) -> some View {
#if DEBUG
        print("DEBUG LOG: \(value)")
#endif
        return self
    }
}
