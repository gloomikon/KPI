//
//  PlacesCustomCell.swift
//  lab5
//
//  Created by Nikolay Zhurba on 12/3/19.
//  Copyright © 2019 Nikolay Zhurba. All rights reserved.
//

import UIKit

class PlacesCustomCell: UITableViewCell {

    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var isFree: UILabel!
    @IBOutlet weak var btn: UIButton!

    var id: Int?

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    @IBAction func btnAction(_ sender: UIButton) {
    }
}
