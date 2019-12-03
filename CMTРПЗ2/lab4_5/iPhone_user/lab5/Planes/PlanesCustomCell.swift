//
//  PlanesCustomCell.swift
//  lab5
//
//  Created by Nikolay Zhurba on 12/2/19.
//  Copyright © 2019 Nikolay Zhurba. All rights reserved.
//

import UIKit

class PlanesCustomCell: UITableViewCell {
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var capacity: UILabel!
    @IBOutlet weak var date: UILabel!
    var id: Int?

    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

}
