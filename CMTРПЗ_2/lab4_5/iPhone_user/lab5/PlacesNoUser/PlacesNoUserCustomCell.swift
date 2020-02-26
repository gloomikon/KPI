import UIKit

class PlacesNoUserCustomCell: UITableViewCell {

    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var isFree: UILabel!

    var id: Int?

    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
}
