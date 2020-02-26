//
//  TicketsCustomCell.swift
//  iPhone_admin
//
//  Created by Nikolay Zhurba on 12/5/19.
//  Copyright © 2019 Nikolay Zhurba. All rights reserved.
//

import UIKit

class TicketsCustomCell: UITableViewCell {
    @IBOutlet weak var customer: UILabel!
    @IBOutlet weak var plane: UILabel!
    @IBOutlet weak var place: UILabel!
    @IBOutlet weak var date: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

}
