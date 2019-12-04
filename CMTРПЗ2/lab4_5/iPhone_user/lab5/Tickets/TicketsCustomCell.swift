import UIKit

class TicketsCustomCell: UITableViewCell {

    @IBOutlet weak var planeName: UILabel!
    @IBOutlet weak var placeName: UILabel!
    @IBOutlet weak var date: UILabel!

    weak var delegate: TicketsViewController?

    var id: Int?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    @IBAction func deleteTicket(_ sender: Any) {
        var components = URLComponents(string:  "http:/localhost:8080/places/")!
        components.queryItems = [
            URLQueryItem(name: "id", value: "\(id!)"),
            URLQueryItem(name: "customer_id", value: nil)
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "POST"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            DispatchQueue.main.async {
                for i in 0 ..< self.delegate!.tickets.count {
                    if (self.delegate!.tickets[i].id == self.id) {
                        self.delegate!.tickets.remove(at: i)
                        break
                    }
                }
                self.delegate!.tableView.reloadData()
            }
        }.resume()
    }
}
