import Foundation
import TRIKOT_FRAMEWORK_NAME

public class TrikotHttpRequestFactory: NSObject, HttpRequestFactory {
    public func request(requestBuilder: RequestBuilder) -> HttpRequest {
        return TrikotHttpRequest(requestBuilder)
    }
}
