//
//  ViewController.swift
//  iPhone_admin
//
//  Created by Nikolay Zhurba on 12/1/19.
//  Copyright © 2019 Nikolay Zhurba. All rights reserved.
//

import UIKit

class AddPlaneController: UIViewController {
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var planeName: UITextField!
    @IBOutlet weak var placeNumber: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        datePicker.minimumDate = Date()
        placeNumber.text = "1"

    }


    @IBAction func updatePlaceNumber(_ sender: UIStepper) {
        placeNumber.text = Int(sender.value).description
    }

    @IBAction func addPlane(_ sender: Any) {
        if (planeName.text == "") {
            let alert = UIAlertController(title: "Error", message: "Name can not be empty", preferredStyle: UIAlertController.Style.alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            self.present(alert, animated: true)
            return
        }
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd HH:mm"
        let date = formatter.string(from: datePicker.date)
        var components = URLComponents(string:  "http:/localhost:8080/planes/")!
        components.queryItems = [
            URLQueryItem(name: "name", value: "\(planeName.text!)"),
            URLQueryItem(name: "capacity", value: placeNumber.text!),
            URLQueryItem(name: "date", value: date),
        ]
        var request = URLRequest(url: components.url!)
        request.httpMethod = "POST"
        let _ = URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else { return }
            let alert = UIAlertController(title: "MESSAGE", message: "Plane successfully added", preferredStyle: UIAlertController.Style.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
            DispatchQueue.main.async {
                self.present(alert, animated: true)
            }
        }.resume()
    }
}

