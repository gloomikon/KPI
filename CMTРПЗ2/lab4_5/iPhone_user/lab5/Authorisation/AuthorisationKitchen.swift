//import Foundation
import UIKit
import FBSDKCoreKit
import FBSDKLoginKit

enum AuthorisationEvent {
    case signIn
    case signUp
}

enum AuthorisationCommand {
    case userAuthorised
}

class AuthorisationKitchen {
    private var delegate: AuthorisationController!


    init(delegate: AuthorisationController) {
        self.delegate = delegate
    }

    public func receive(event: AuthorisationEvent) {
        switch event {
        case .signUp:
            performSignUp()
        case .signIn:
            performSignIn()
        }
    }

    private func performSignUp() {
        let  manager = LoginManager()
        manager.logIn(permissions: ["public_profile", "email"], from: delegate) { (result, error) in
            guard error == nil else { return }
            print("token = \(String(describing: result?.token))")
            let graphRequest = GraphRequest(graphPath: "me",
                                            parameters: ["fields":"id, email, name, picture.width(480).height(480)"])
            graphRequest.start(completionHandler: { (connection, result, error) -> Void in
                guard error == nil else { return }
                let result = result as! NSDictionary
                let id = Int(result["id"] as! String)!
                let name = result["name"] as! String
                let url = ((result["picture"] as! NSDictionary)["data"] as! NSDictionary)["url"] as! String
                user = User(id: id, name: name, url: url)
                ServerAPI.shared.postUser(user!, delegate: self)
            })
        }
    }
    private func performSignIn() {
        let  manager = LoginManager()
        manager.logIn(permissions: ["public_profile", "email"], from: delegate) { (result, error) in
            guard error == nil else { return }
            print("token = \(String(describing: result?.token))")
            let graphRequest = GraphRequest(graphPath: "me",
                                            parameters: ["fields":"id, email, name, picture.width(480).height(480)"])
            graphRequest.start(completionHandler: { (connection, result, error) -> Void in
                guard error == nil else { return }
                let result = result as! NSDictionary
                let id = Int(result["id"] as! String)!
                let name = result["name"] as! String
                let url = ((result["picture"] as! NSDictionary)["data"] as! NSDictionary)["url"] as! String
                user = User(id: id, name: name, url: url)
                print("user = \(String(describing: user))")
                ServerAPI.shared.getUser(id: user!.id, delegate: self)
            })
        }
    }

}

extension AuthorisationKitchen: AnyKitchenProtocol {
    func performActionAfterRequest(result: Bool) {
        if (result == false) {
            delegate.displayError(message: "Error occured while authorisation")
        } else {
            delegate.perform(command: .userAuthorised)
        }
    }
}
