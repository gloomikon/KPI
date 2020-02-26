import Foundation

class ServerAPI {
    static let shared = ServerAPI()

    private init() {

    }

    func getUser(id: Int, delegate: AnyKitchenProtocol)  {
        var components = URLComponents(string:  "http:/localhost:8080/customers/")!
        components.queryItems = [
            URLQueryItem(name: "id", value: "\(id)"),
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "GET"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            let jsonResponse = try! JSONSerialization.jsonObject(with: data!, options: [])
            let jsonArray = jsonResponse as! [[String: Any]]
            print(jsonArray)
            let success = jsonArray.count > 0
            DispatchQueue.main.async {
                delegate.performActionAfterRequest(result: success)
            }
        }.resume()
    }

    func postUser(_ user: User, delegate: AnyKitchenProtocol) {
        var components = URLComponents(string:  "http:/localhost:8080/customers/")!
        components.queryItems = [
            URLQueryItem(name: "id", value: "\(user.id)"),
            URLQueryItem(name: "name", value: "\(user.name)"),
            URLQueryItem(name: "url", value: "\(user.url)"),
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "POST"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else {return}
            let success = String(data: data!, encoding: .utf8).flatMap(Bool.init)!
            DispatchQueue.main.async {
                delegate.performActionAfterRequest(result: success)
            }
        }.resume()
    }

    func getPlanes() {

    }

    func postTicket() {

    }

    func getTicketsForPlane() {

    }

    func getTicketsForUser() {

    }
}
