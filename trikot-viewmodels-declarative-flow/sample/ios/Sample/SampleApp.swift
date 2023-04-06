import SwiftUI

@main
struct SampleApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    var body: some Scene {
        WindowGroup {
            RootView(
                platformNavigationController: appDelegate.platformNavigationController,
                viewModelFactory: appDelegate.appEntryPoint.viewModelFactory)
        }
    }
}
