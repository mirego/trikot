import SwiftUI
import Jasper

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

        languageCodes.forEach { languageCode in
            let i18N = DefaultI18N()
            KwordLoader.shared.setCurrentLanguageCode(i18N: i18N, basePaths: ["translation"], code: languageCode)
            i18NList.append(i18N)
        }

        let map = [String: I18N](uniqueKeysWithValues: zip(languageCodes, i18NList))
        return FlowMultiLanguageI18N(initialLanguage: "en", languages: map)
    }()
}
