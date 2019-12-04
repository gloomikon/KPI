import UIKit

struct User {
    let id: Int
    let name: String
    let url: String
}

var user: User?

class AuthorisationController: UIViewController {
    @IBOutlet weak var signIn: UIButton!
    @IBOutlet weak var signUp: UIButton!
    @IBOutlet weak var viewPlanes: UIButton!

    let layer = CAGradientLayer()
    var kitchen: AuthorisationKitchen!

    override func viewDidLoad() {
        super.viewDidLoad()
        kitchen = AuthorisationKitchen(delegate: self)
        layer.frame = view.bounds
        signIn.layer.cornerRadius = 10
        signUp.layer.cornerRadius = 10
        viewPlanes.layer.cornerRadius = 10
        layer.colors = [UIColor.white.cgColor, UIColor.purple.cgColor, UIColor.black.cgColor]
        view.layer.insertSublayer(layer, at: 0)
    }

    @IBAction func loginWithFacebook(_ sender: Any) {
        kitchen.receive(event: .signIn)
    }

    @IBAction func signUpWithFacebook(_ sender: Any) {
        kitchen.receive(event: .signUp)
    }

    private func gotoSecondPart() {
        performSegue(withIdentifier: "gotoSecondPart", sender: nil)
    }

    func perform(command: AuthorisationCommand) {
        switch command {
        case .userAuthorised:
            gotoSecondPart()
        }
    }
}

