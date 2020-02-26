import UIKit

class ProfileController: UIViewController {
    @IBOutlet weak var photo: UIImageView!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var indicator: UIActivityIndicatorView!

    override func viewDidLoad() {
        super.viewDidLoad()
        let layer = CAGradientLayer()
        layer.frame = view.bounds
        layer.colors = [UIColor.red.cgColor, UIColor.orange.cgColor, UIColor.purple.cgColor]
        view.layer.insertSublayer(layer, at: 0)
        name.text = user!.name
        let url = URL(string: user!.url)
        indicator.startAnimating()
        URLSession.shared.dataTask(with: url!) {
            (data, resp, err) in
            guard let data = data, err == nil else { return }
            DispatchQueue.main.async {
                self.photo.image = UIImage(data: data)
                self.indicator.hidesWhenStopped = true
                self.indicator.stopAnimating()
            }
        }.resume()
    }
}
