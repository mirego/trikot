import Foundation
import TrikotViewmodelsDeclarativeSample
import Trikot_http

class AppBootstrap {
    static func initialize() {
        initializeTrikotHttp()
    }

    private static func initializeTrikotHttp() {
        HttpConfiguration().httpRequestFactory = TrikotHttpRequestFactory()
        HttpConfiguration().connectivityPublisher = TrikotConnectivityService.shared.publisher
    }
}
