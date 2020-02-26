//
//  PlaneCustomCell.swift
//  iPhone_admin
//
//  Created by Nikolay Zhurba on 12/4/19.
//  Copyright © 2019 Nikolay Zhurba. All rights reserved.
//

import UIKit

class PlaneCustomCell: UITableViewCell {
    @IBOutlet weak var planeDate: UILabel!
    @IBOutlet weak var planeCapacity: UILabel!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var planeName: UITextField!

    var delegate: PlaneViewController?
    
    var id: Int?
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

    @IBAction func updatePlane(_ sender: Any) {
        guard planeName.text != "" else {return}
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd HH:mm"
        let date = formatter.string(from: datePicker.date)
        var components = URLComponents(string:  "http:/localhost:8080/planes/")!
        components.queryItems = [
            URLQueryItem(name: "id", value: "\(id!)"),
            URLQueryItem(name: "name", value: planeName.text!),
            URLQueryItem(name: "date", value: date)
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "POST"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            DispatchQueue.main.async {
                for i in 0 ..< self.delegate!.allPlanes.count {
                    if self.delegate!.allPlanes[i].id == self.id {
                        self.delegate!.allPlanes[i].date = self.datePicker.date
                        self.delegate!.allPlanes[i].name = self.planeName.text!
                        break
                    }
                }
                self.planeDate.text = date
                self.delegate!.tableView.reloadData()
            }

            }.resume()
    }
    @IBAction func deletePlane(_ sender: Any) {
        var components = URLComponents(string:  "http:/localhost:8080/planes/")!
        components.queryItems = [
            URLQueryItem(name: "id", value: "\(id!)"),
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "DELETE"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            DispatchQueue.main.async {
                for i in 0 ..< self.delegate!.allPlanes.count {
                    if self.delegate!.allPlanes[i].id == self.id {
                        self.delegate!.allPlanes.remove(at: i)
                        break
                    }
                }
                self.delegate!.tableView.reloadData()
            }

        }.resume()
    }
}
