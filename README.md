# What to Replace
patron-project-name -> my-project-name (tv5-multiplatform) - (settings.gradle)

PatronProjectName -> TV5 (android/build.gradle, bundleName)

Patron App Name -> Your App Name (values/strings.xml, DisplayNAme)

TrikotFrameworkName -> MyFrameworkName (TV5mobile.podspec) (file + content - TrikotFrameworkName.podspec, common/build.gradle, Podfile)

com.trikot.project -> com.my-company.project-name 
    (rename directory to match package name (android + common), 
    rename packages, android/build.gradle, AndroidManifest, BundleIdentifier)

Question pour JD:
- Proguard et Keystore
- Environment
