import Trikot_http
import Trikot_kword
import Trikot_metaviews
import TrikotFrameworkName
import UIKit


@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]) -> Bool {
        Environment().flavor = currentFlavor()
        HttpConfiguration().httpRequestFactory = TrikotHttpRequestFactory()
        HttpConfiguration().connectivityPublisher = TrikotConnectivityService.shared.publisher
        MetaImageResourceManager.shared = SampleImageResourceProvider()
        TrikotKword.shared.setCurrentLanguage("en")

        let window = UIWindow(frame: UIScreen.main.bounds)
        self.window = window

        window.rootViewController = SampleViewController()

        window.makeKeyAndVisible()

        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        TrikotConnectivityService.shared.stop()
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
    }

    func applicationWillEnterForeground(_ application: UIApplication) {}

    func applicationDidBecomeActive(_ application: UIApplication) {
        TrikotConnectivityService.shared.start()
    }

    func applicationWillTerminate(_ application: UIApplication) {}

    func currentFlavor() -> Environment.Flavor {
        switch (Bundle.main.object(forInfoDictionaryKey: "Environment") ?? "debug") as! String {
        case "debug":
            return .debug
        case "qa":
            return .qa
        case "staging":
            return .staging
        default:
            return Environment.Flavor.release_
        }
    }
}
