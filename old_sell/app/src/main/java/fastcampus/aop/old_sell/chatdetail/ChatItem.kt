package fastcampus.aop.old_sell.chatdetail

data class ChatItem(
    val sendId: String,
    val message: String
) {
    constructor() : this("", "")
}
