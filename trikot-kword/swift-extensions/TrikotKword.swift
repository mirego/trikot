import Foundation
import Trikot
import TRIKOT_FRAMEWORK_NAME

public class TrikotKword: NSObject {
    public static let translationBasePaths = [
        "translation"
    ]
    
    public static let shared = TrikotKword()

    public func setCurrentLanguage(
        _ languageCode: String,
        basePaths: [String] = TrikotKword.translationBasePaths,
        fileSystem: FileSystem?,
        internalStoragePath: String?
    ) {
        setCurrentLanguage(i18N: KWord.shared, basePaths: basePaths, fileSystem: fileSystem, internalStoragePath: internalStoragePath, codes: languageCode)
    }

    public func setCurrentLanguage(
        i18N: I18N,
        languageCode: String,
        fileSystem: FileSystem?,
        internalStoragePath: String?,
        basePaths: [String] = TrikotKword.translationBasePaths
    ) {
        setCurrentLanguage(i18N: i18N, basePaths: basePaths, fileSystem: fileSystem, internalStoragePath: internalStoragePath, codes: languageCode)
    }

    public func setCurrentLanguage(
        i18N: I18N,
        basePaths: [String] = TrikotKword.translationBasePaths,
        fileSystem: FileSystem?,
        internalStoragePath: String?,
        codes: String...
    ) {
        KwordLoader.shared.setCurrentLanguageCodes(
            i18N: i18N, 
            basePaths: basePaths,
            fileSystem: fileSystem,
            cacheDirPath: internalStoragePath,
            codes: KotlinArray<NSString>(size: Int32(codes.count)) { index in
            codes[index.intValue] as NSString
            }
        )
    }
}
