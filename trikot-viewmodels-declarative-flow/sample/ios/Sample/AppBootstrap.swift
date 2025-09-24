import Foundation
import Jasper
import Trikot

class AppBootstrap {
    static func initialize() {
        initializeTrikotViewModels()
        initializeTrikotKword()
    }

    private static func initializeTrikotViewModels() {
        TrikotViewModelDeclarative.shared.initialize(
            imageProvider: SampleImageProvider(),
            spanStyleProvider: SampleSpanStyleProvider()
        )
    }

    private static func initializeTrikotKword() {
        TrikotKword.shared.setCurrentLanguage("en")
    }
}
