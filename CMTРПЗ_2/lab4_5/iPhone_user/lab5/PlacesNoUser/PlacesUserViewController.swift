import UIKit

class PlacesNoUserViewController: UIViewController {
    var plane: Plane?
    var places = [Place]()

    @IBOutlet weak var planeName: UILabel!
    @IBOutlet weak var planeCapacity: UILabel!
    @IBOutlet weak var planeDate: UILabel!
    @IBOutlet weak var tableView: UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()
        planeName.text = plane!.name
        planeCapacity.text = String(plane!.capacity)
        let formatter = DateFormatter()
        formatter.dateFormat = "yy/MM/dd HH:mm"
        formatter.timeZone = TimeZone(abbreviation: "GMT")
        let date = formatter.string(from: plane!.date)
        planeDate.text = date
    }

    override func viewWillAppear(_ animated: Bool) {
        places.removeAll()
        var components = URLComponents(string:  "http:/localhost:8080/places/")!
        components.queryItems = [
            URLQueryItem(name: "plane_id", value: "\(plane!.id)"),
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "GET"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            let jsonResponse = try! JSONSerialization.jsonObject(with: data!, options: [])
            let jsonArray = jsonResponse as! [[String: Any]]
            for obj in jsonArray {
                print(obj)
                let name = obj["name"] as! String
                let id = obj["id"] as! Int
                let customerId = obj["customerId"] as? Int
                let planeId = obj["planeId"] as! Int
                let place = Place(id: id, name: name, plane_id: planeId, customer_id: customerId)
                print(place)
                self.places.append(place)
            }
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        }.resume()
    }
}

extension PlacesNoUserViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return places.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "placeNoUserCell") as! PlacesNoUserCustomCell
        cell.name.text = places[indexPath.row].name
        cell.id = places[indexPath.row].id
        if places[indexPath.row].customer_id != nil {
            cell.isFree.text = "Booked"
        } else {
            cell.isFree.text = "Free"
        }
        return cell
    }
}
