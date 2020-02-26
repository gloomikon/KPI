import UIKit

struct Ticket {
    let id: Int
    let planeName: String
    let placeName: String
    let date: Date
}

class TicketsViewController: UIViewController {
    var tickets = [Ticket]()

    @IBOutlet weak var tableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func viewWillAppear(_ animated: Bool) {
        tickets.removeAll()
        var components = URLComponents(string:  "http:/localhost:8080/tickets/user")!
        components.queryItems = [
            URLQueryItem(name: "user_id", value: "\(user!.id)"),
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "GET"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            let jsonResponse = try! JSONSerialization.jsonObject(with: data!, options: [])
            let jsonArray = jsonResponse as! [[String: Any]]
            for obj in jsonArray {
                let planeName = obj["planeName"] as! String
                let placeName = obj["placeName"] as! String
                let id = obj["id"] as! Int
                let dateStr = obj["date"] as! String
                print(dateStr)
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "MMM d, yyyy, hh:mm:ss a"
                dateFormatter.locale = Locale(identifier: "en_US_POSIX")
                dateFormatter.timeZone = TimeZone(abbreviation: "GMT")
                let date = dateFormatter.date(from:dateStr)!
                let ticket = Ticket(id: id, planeName: planeName, placeName: placeName, date: date)
                self.tickets.append(ticket)
            }
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        }.resume()
    }
}

extension TicketsViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return tickets.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ticketCell") as! TicketsCustomCell
        cell.placeName.text = tickets[indexPath.row].placeName
        cell.planeName.text = tickets[indexPath.row].planeName
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd HH:mm"
        formatter.timeZone = TimeZone(abbreviation: "GMT")
        let date = formatter.string(from: tickets[indexPath.row].date)
        cell.date.text = date
        cell.id = tickets[indexPath.row].id
        cell.delegate = self
        return cell
    }


}
