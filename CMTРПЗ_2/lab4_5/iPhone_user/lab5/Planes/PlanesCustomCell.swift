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
