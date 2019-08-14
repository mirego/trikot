import Foundation
import TRIKOT_FRAMEWORK_NAME

public class TrikotHttpRequest: NSObject, HttpRequest {
    let requestBuilder: RequestBuilder

    init(_ requestBuilder: RequestBuilder) {
        self.requestBuilder = requestBuilder
    }

    public func execute(cancellableManager: CancellableManager) -> Publisher {
        let resultPublisher = SimplePublisher(value: nil)

        if let url = URL(string: (requestBuilder.baseUrl ?? "") + (requestBuilder.path ?? "")) {
            let urlRequest = NSMutableURLRequest(url: url, cachePolicy: requestBuilder.nsCachePolicy(), timeoutInterval: TimeInterval(requestBuilder.timeout))
            urlRequest.httpMethod = requestBuilder.method.name.uppercased()

            requestBuilder.headers.forEach { key, value in
                urlRequest.setValue(value, forHTTPHeaderField: key)
            }

            if let body = requestBuilder.body as? String {
                urlRequest.httpBody = body.data(using: .utf8)
            }

            let sessionTask = URLSession.shared.dataTask(with: urlRequest as URLRequest) { (data, urlResponse, error) in
                if let error = error {
                    resultPublisher.error = KotlinThrowable(message: error.localizedDescription)
                } else {
                    let iosResponse = TrikotHttpResponse(data: data, response: urlResponse)
                    MrFreeze().freeze(objectToFreeze: iosResponse)
                    resultPublisher.value = iosResponse
                }
            }
            sessionTask.resume()
        } else {
            resultPublisher.error = KotlinThrowable(message: "Unable to create a valid URL")
        }

        return resultPublisher
    }
}

extension RequestBuilder {
    func nsCachePolicy() -> NSURLRequest.CachePolicy {
        switch method {
        case HttpMethod.post, HttpMethod.put, HttpMethod.delete_: return NSURLRequest.CachePolicy.reloadIgnoringCacheData
        default: return cachePolicy.nsCachePolicy()
        }
    }
}

extension CachePolicy {
    func nsCachePolicy() -> NSURLRequest.CachePolicy {
        switch self {
        case .reloadIgnoringCacheData : return NSURLRequest.CachePolicy.reloadIgnoringCacheData
        default: return NSURLRequest.CachePolicy.useProtocolCachePolicy
        }
    }
}
