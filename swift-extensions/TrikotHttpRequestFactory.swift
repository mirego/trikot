import Foundation
import TRIKOT_FRAMEWORK_NAME

class TrikotHttpRequestFactory: NSObject, HttpRequestFactory {
    func request(requestBuilder: RequestBuilder) -> HttpRequest {
        return TrikotHttpRequest(requestBuilder)
    }
}
