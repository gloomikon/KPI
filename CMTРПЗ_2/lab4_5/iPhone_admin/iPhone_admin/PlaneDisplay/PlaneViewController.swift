struct Plane {
    let id: Int
    var name: String
    let capacity: Int
    var date: Date
}

import UIKit

class PlaneViewController: UIViewController {
    var allPlanes = [Plane]()

    @IBOutlet weak var tableView: UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.estimatedRowHeight = 250
        tableView.rowHeight = UITableView.automaticDimension
    }
    

    override func viewWillAppear(_ animated: Bool) {
        allPlanes.removeAll()
        let components = URLComponents(string:  "http:/localhost:8080/planes/")!
        var request = URLRequest(url: components.url!)
        request.httpMethod = "GET"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            let jsonResponse = try! JSONSerialization.jsonObject(with: data!, options: [])
            let jsonArray = jsonResponse as! [[String: Any]]
            for obj in jsonArray {
                let name = obj["name"] as! String
                let id = obj["id"] as! Int
                let capacity = obj["capacity"] as! Int
                let dateStr = obj["date"] as! String
                print(dateStr)
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "MMM d, yyyy, hh:mm:ss a"
                dateFormatter.locale = Locale(identifier: "en_US_POSIX")
                dateFormatter.timeZone = TimeZone(abbreviation: "GMT")
                let date = dateFormatter.date(from:dateStr)!
                let plane = Plane(id: id, name: name, capacity: capacity, date: date)
                print(plane)
                self.allPlanes.append(plane)
            }
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
            }.resume()
    }

}

extension PlaneViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return allPlanes.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "planeCell") as! PlaneCustomCell
        cell.planeName.text = allPlanes[indexPath.row].name
        cell.planeCapacity.text = String(allPlanes[indexPath.row].capacity)
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd HH:mm"
        formatter.timeZone = TimeZone(abbreviation: "GMT")
        let date = formatter.string(from: allPlanes[indexPath.row].date)
        cell.planeDate.text = date
        cell.id = allPlanes[indexPath.row].id
        cell.datePicker.timeZone = TimeZone(abbreviation: "GMT")
        cell.datePicker.date = allPlanes[indexPath.row].date
        cell.delegate = self
        return cell
    }


}
