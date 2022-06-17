import Foundation
import TRIKOT_FRAMEWORK_NAME
import Trikot

class AppBootstrap {
    static func initialize() {
        initializeTrikotViewModels()
        initializeTrikotKword()
    }

    private static func initializeTrikotViewModels() {
        TrikotViewModelDeclarative.shared.initialize(
            imageProvider: SampleImageProvider()
        )
    }

    private static func initializeTrikotKword() {
        TrikotKword.shared.setCurrentLanguage("en")
    }
}
