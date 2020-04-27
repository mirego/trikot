import Foundation
import TRIKOT_FRAMEWORK_NAME

public class TrikotKword: NSObject {
    public static let shared = TrikotKword()

    public func setCurrentLanguage(_ languageCode: String) {
        setCurrentLanguage(i18N: KWord(), languageCode: languageCode)
    }

    public func setCurrentLanguage(i18N: I18N, languageCode: String) {
        setCurrentLanguage(i18N: i18N, codes: languageCode)
    }

    public func setCurrentLanguage(i18N: I18N, codes: String...) {
        var allStrings = [String: String]()
        var variant = [String]()
        for code in codes {
            variant.append(code)
            let variantPath = variant.joined(separator: ".")
            do {
                let translationBundle = Bundle(for: KWord.self)
                let contents = try String(contentsOfFile: translationBundle.path(forResource: "translation.\(variantPath)", ofType: "json") ?? "")
                let decoder = JSONDecoder()
                let strings = try decoder.decode([String: String].self, from: contents.data(using: .utf8)!)
                allStrings.merge(strings, uniquingKeysWith: { (_, last) in last })
            } catch let error {
                print("Unable to load translation: \(error)")
            }
        }
        KWord().changeLocaleStrings(strings: allStrings)
    }
}

