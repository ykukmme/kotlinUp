package fastcampus.aop.book.model

import com.google.gson.annotations.SerializedName

data class SearchBook(
    @SerializedName("items") val books: List<Book>
)
