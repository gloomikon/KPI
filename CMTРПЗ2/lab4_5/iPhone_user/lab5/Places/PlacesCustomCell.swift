import UIKit

class PlacesCustomCell: UITableViewCell {

    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var isFree: UILabel!
    @IBOutlet weak var btn: UIButton!
    weak var delegate: PlacesViewController!

    var id: Int?

    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

    @IBAction func btnAction(_ sender: UIButton) {
        var components = URLComponents(string:  "http:/localhost:8080/places/")!
        components.queryItems = [
            URLQueryItem(name: "id", value: "\(id!)"),
            URLQueryItem(name: "customer_id", value: "\(user!.id)")
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "POST"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            DispatchQueue.main.async {
                sender.isHidden = true
                self.isFree.text = "Booked"
            }
        }.resume()
    }
}
