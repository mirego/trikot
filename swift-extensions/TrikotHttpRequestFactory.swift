import Foundation
import TRIKOT_FRAMEWORK_NAME

public class TrikotHttpRequestFactory: NSObject, HttpRequestFactory {
    private let httpLogLevel: TrikotHttpLogLevel

    public init(httpLogLevel: TrikotHttpLogLevel = .none) {
        self.httpLogLevel = httpLogLevel
        super.init()
    }

    public func request(requestBuilder: RequestBuilder) -> HttpRequest {
        return TrikotHttpRequest(requestBuilder, httpLogLevel: httpLogLevel)
    }
}
