import SwiftUI
import Jasper

struct ContentView: View {
    
    @ObservedObject var text: ObservableFlowWrapper<NSString>
    @ObservedObject var buttonText: ObservableFlowWrapper<NSString>
    let textProvider: TextProvider
    
    init(textProvider: TextProvider) {
        self.textProvider = textProvider
        text = ObservableFlowWrapper<NSString>(textProvider.text, initial: "")
        buttonText = ObservableFlowWrapper<NSString>(textProvider.buttonText, initial: "")
    }
    
    var body: some View {
        Text("\(text.value)").padding()
        Button("\(buttonText.value)", action: { textProvider.toggleLanguage() })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        let serviceLocator = ServiceLocatorImpl(i18N: FlowMultiLanguageI18N.sample)
        ContentView(textProvider: serviceLocator.textProvider)
    }
}
