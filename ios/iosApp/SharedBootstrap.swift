import Foundation
import TrikotFrameworkName

class Core {
    static let shared: Core = Core()
    private var bootstrap: Bootstrap = Bootstrap()
    let viewModelFactory: ViewModelFactory

    init() {
        viewModelFactory = bootstrap.viewModelFactory
    }
}
