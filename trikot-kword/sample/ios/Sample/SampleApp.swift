import SwiftUI
import TRIKOT_FRAMEWORK_NAME

@main
struct SampleApp: App {
    var body: some Scene {
        let serviceLocator = ServiceLocatorImpl(i18N: FlowMultiLanguageI18N.sample)
        WindowGroup {
            ContentView(textProvider: serviceLocator.textProvider)
        }
    }
}

extension FlowMultiLanguageI18N {
    static var sample: FlowMultiLanguageI18N = {
        let languageCodes = ["en", "fr"]
        var i18NList: [I18N] = []
//        let fileSystem = FileSystem.companion.SYSTEM
//        let internalStoragePath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first?.absoluteString ?? nil

        languageCodes.forEach { languageCode in
            let i18N = DefaultI18N(debugMode: false)
            KwordLoader.shared.setCurrentLanguageCode(
                i18N: i18N,
                basePaths: ["translation"],
                fileSystem: nil,
                cacheDirPath: nil,
                code: languageCode
            )
            i18NList.append(i18N)
        }

        let map = [String: I18N](uniqueKeysWithValues: zip(languageCodes, i18NList))
        return FlowMultiLanguageI18N(initialLanguage: "en", languages: map)
    }()
}
