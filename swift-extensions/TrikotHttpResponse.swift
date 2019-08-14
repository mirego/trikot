import Foundation
import TRIKOT_FRAMEWORK_NAME

class TrikotHttpResponse: NSObject, HttpResponse {
    var bodyStream: Kotlinx_serialization_runtimeInputStream?
    var bodyString: String?

    var headers: [String: String]

    var source: HttpResponseResponseSource

    var statusCode: Int32 = 0

    init(data: Data?, response: URLResponse?) {
        var headers = [String: String]()
        source = HttpResponseResponseSource.unknown
        if let response = response as? HTTPURLResponse {
            response.allHeaderFields.forEach {(arg) in
                let (key, value) = arg
                if let key = key as? String, let value = value as? String {
                    headers[key] = value
                }
            }
            statusCode = Int32(response.statusCode)
        }
        if let data = data {
            bodyString = String(data: data, encoding: .utf8)
        }

        self.headers = headers
    }
}
