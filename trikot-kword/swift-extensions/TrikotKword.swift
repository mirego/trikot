import Foundation
import Trikot
import TRIKOT_FRAMEWORK_NAME

public class TrikotKword: NSObject {
    public static let translationBasePaths = [
        "translation"
    ]
    
    public static let shared = TrikotKword()

    public func setCurrentLanguage(_ languageCode: String, basePaths: [String] = TrikotKword.translationBasePaths) {
        setCurrentLanguage(i18N: KWord.shared, basePaths: basePaths, codes: languageCode)
    }

    public func setCurrentLanguage(i18N: I18N, languageCode: String, basePaths: [String] = TrikotKword.translationBasePaths) {
        setCurrentLanguage(i18N: i18N, basePaths: basePaths, codes: languageCode)
    }

    public func setCurrentLanguage(i18N: I18N, basePaths: [String] = TrikotKword.translationBasePaths, codes: String...) {
        KwordLoader.shared.setCurrentLanguageCodes(i18N: i18N, basePaths: basePaths, codes: KotlinArray<NSString>(size: Int32(codes.count)) { index in
            codes[index.intValue] as NSString
        })
    }
}
