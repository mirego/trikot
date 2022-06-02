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
        let i18NEn = DefaultI18N()
        KwordLoader.shared.setCurrentLanguageCode(i18N: i18NEn, basePaths: basePaths, code: "en")
        let i18NFr = DefaultI18N()
        KwordLoader.shared.setCurrentLanguageCode(i18N: i18NFr, basePaths: basePaths, code: "fr")
        
        let i18NList = [i18NEn, i18NFr]
        let languageCodes = ["en", "fr"]
        
        let map = [String: I18N](uniqueKeysWithValues: zip(languageCodes, i18NList))
        return FlowMultiLanguageI18N(initialLanguage: "en", languages: map)
    }
}
