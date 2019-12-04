import UIKit

struct Plane {
    let id: Int
    let name: String
    let capacity: Int
    let date: Date
}

class PlanesViewController: UIViewController {
    var allPlanes = [Plane]()
    var sortedPlanes: [Plane]?
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var planeName: UITextField!


    override func viewDidLoad() {
        super.viewDidLoad()
        datePicker.timeZone = TimeZone(abbreviation: "GMT")
        sortedPlanes = allPlanes
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
                self.sortedPlanes = self.allPlanes
                self.tableView.reloadData()
            }
        }.resume()
    }
    @IBAction func filterPlanes(_ sender: Any) {
        sortedPlanes = allPlanes.filter({ (plane) -> Bool in
            print(plane.date)
            print(self.datePicker.date)
            print(Calendar.current.compare(plane.date, to: self.datePicker.date, toGranularity: .minute))
            return Calendar.current.compare(plane.date, to: self.datePicker.date, toGranularity: .minute) == .orderedSame || plane.name.contains(planeName.text!)
        })
        tableView.reloadData()
    }

    @IBAction func cancelFilter(_ sender: Any) {
        sortedPlanes = allPlanes
        tableView.reloadData()
    }
}

extension PlanesViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sortedPlanes!.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! PlanesCustomCell
        cell.name.text = sortedPlanes![indexPath.row].name
        cell.capacity.text = String(sortedPlanes![indexPath.row].capacity)
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd HH:mm"
        formatter.timeZone = TimeZone(abbreviation: "GMT")
        let date = formatter.string(from: sortedPlanes![indexPath.row].date)
        cell.date.text = date
        cell.id = sortedPlanes![indexPath.row].id
        return cell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let vc = storyboard?.instantiateViewController(withIdentifier: "placesController") as! PlacesViewController
        vc.plane = sortedPlanes![indexPath.row]
        navigationController?.pushViewController(vc, animated: true)
    }
}
