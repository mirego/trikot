import Foundation
import TrikotViewmodelsDeclarativeSample
import Trikot_http
import Trikot_kword

class AppBootstrap {
    static func initialize() {
        initializeTrikotHttp()
    }

    private static func initializeTrikotHttp() {
        HttpConfiguration().httpRequestFactory = TrikotHttpRequestFactory()
        HttpConfiguration().connectivityPublisher = TrikotConnectivityService.shared.publisher
        TrikotKword.shared.setCurrentLanguage("en")
    }
}
