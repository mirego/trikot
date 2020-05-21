import Foundation
import TrikotFrameworkName

class Core {
    static let shared = Core()
    private var bootstrap = Bootstrap()
    let viewModelControllerFactory: ViewModelControllerFactory

    init() {
        viewModelControllerFactory = bootstrap.viewModelControllerFactory
    }
}
