import Foundation
import TrikotFrameworkName

class Core {
    static let shared = Core()
    private var bootstrap = Bootstrap()
    let viewModelFactory: ViewModelFactory

    init() {
        viewModelFactory = bootstrap.viewModelFactory
    }
}
