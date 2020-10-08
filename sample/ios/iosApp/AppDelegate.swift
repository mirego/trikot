import UIKit
import ViewModelsSample
import Trikot_viewmodels

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate, NavigationDelegate {
    var window: UIWindow?
    let navigationController = UINavigationController()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        Environment().flavor = CurrentFlavor()
        ImageViewModelResourceManager.shared = SampleImageResourceProvider()
        TextAppearanceViewModelResourceManager.shared = SampleTextAppearanceResourceProvider()

        let window = UIWindow(frame: UIScreen.main.bounds)
        self.window = window

        navigationController.viewControllers = [ListViewController(listViewModel: HomeViewModelImpl(delegate: self))]
        window.rootViewController = navigationController

        window.makeKeyAndVisible()

        return true
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
    }

    func applicationWillEnterForeground(_ application: UIApplication) {}

    func applicationWillTerminate(_ application: UIApplication) {}

    func CurrentFlavor() -> Environment.Flavor {
        switch (Bundle.main.object(forInfoDictionaryKey: "Environment") ?? "debug") as! String {
        case "debug": return .debug
        case "qa": return .qa
        case "staging": return .staging
        default: return Environment.Flavor.release_
        }
    }

    func navigateTo(destination: Destination) {
        navigationController.pushViewController(ListViewController(listViewModel: destination.getViewModel(self)), animated: true)
    }

    func showAlert(text: String) {
        let alert = UIAlertController(title: text, message: "", preferredStyle: .alert)

        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))

        navigationController.present(alert, animated: true)
    }
}
