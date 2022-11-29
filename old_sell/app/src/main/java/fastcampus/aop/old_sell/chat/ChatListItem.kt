package fastcampus.aop.old_sell.chat

data class ChatListItem(
    val buyId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long
) {
    constructor() : this("", "", "", 0)
}
