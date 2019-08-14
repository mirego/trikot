import Foundation
import TRIKOT_FRAMEWORK_NAME

public class TrikotHttpResponse: NSObject, HttpResponse {
    public var bodyString: String?

    public var headers: [String: String]

    public var source: HttpResponseResponseSource

    public var statusCode: Int32 = 0

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
