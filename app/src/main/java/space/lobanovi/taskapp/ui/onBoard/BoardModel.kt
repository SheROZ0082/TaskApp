package space.lobanovi.taskapp.ui.onBoard

data class BoardModel (
    var img: Int?= null,
    var title: String?=null,
    var description: String?=null,
    var isLast: Boolean? = false
        ):java.io.Serializable