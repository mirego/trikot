import SwiftUI
import TRIKOT_FRAMEWORK_NAME

@main
struct SampleApp: App {
    let basePaths = ["translation"]
    
    var body: some Scene {
        let serviceLocator = ServiceLocatorImpl(i18N: createI18NMap())
        WindowGroup {
            ContentView(textProvider: serviceLocator.textProvider)
        }
    }
    
    private func createI18NMap() -> FlowMultiLanguageI18N {
        let languageCodes = ["en", "fr"]
        var i18NList: [I18N] = []
        
        languageCodes.forEach { languageCode in
            let i18N = DefaultI18N()
            KwordLoader.shared.setCurrentLanguageCode(i18N: i18N, basePaths: basePaths, code: languageCode)
            i18NList.append(i18N)
        }
        
        let map = [String: I18N](uniqueKeysWithValues: zip(languageCodes, i18NList))
        return FlowMultiLanguageI18N(initialLanguage: "en", languages: map)
    }
}
