import Foundation
import TrikotViewmodelsDeclarativeSample
import Trikot_http
import Trikot_kword

class AppBootstrap {
    static func initialize() {
        initializeTrikotHttp()
        initializeTrikotViewModels()
        initializeTrikotKword()
    }

    private static func initializeTrikotViewModels() {
        TrikotViewModelDeclarative.shared.initialize(
            imageProvider: SampleImageProvider()
        )
    }

    private static func initializeTrikotHttp() {
        HttpConfiguration().httpRequestFactory = TrikotHttpRequestFactory()
        HttpConfiguration().connectivityPublisher = TrikotConnectivityService.shared.publisher
    }

    private static func initializeTrikotKword() {
        TrikotKword.shared.setCurrentLanguage("en")
    }
}
