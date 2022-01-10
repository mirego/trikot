import Foundation
import TRIKOT_FRAMEWORK_NAME

public class TrikotHttpResponse: NSObject, HttpResponse {

    public var bodyByteArray: KotlinByteArray?

    public var headers: [String: String]

    public var source: HttpResponseResponseSource

    public var statusCode: Int32 = 0

    @objc
    public init(data: Data?, response: URLResponse?) {
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
        if let data = data, data.count > 0 {
            bodyByteArray = MrFreeze().freeze(objectToFreeze: ByteArrayNativeUtils().convert(data: data)) as? KotlinByteArray
        } else {
            bodyByteArray = MrFreeze().freeze(objectToFreeze: KotlinByteArray(size: 0)) as? KotlinByteArray
        }

        self.headers = headers
    }
}
