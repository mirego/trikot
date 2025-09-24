import Foundation
import Jasper

open class TrikotHttpRequestFactory: NSObject, HttpRequestFactory {
    private let httpLogLevel: TrikotHttpLogLevel

    public init(httpLogLevel: TrikotHttpLogLevel = .none) {
        self.httpLogLevel = httpLogLevel
        super.init()
    }

    open func request(requestBuilder: RequestBuilder) -> HttpRequest {
        return TrikotHttpRequest(requestBuilder, httpLogLevel: httpLogLevel)
    }
}
