package fastcampus.aop.book.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    @SerializedName("isbn") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("discount") val discount: String,
    @SerializedName("image") val image: String,
    @SerializedName("link") val link: String

) : Parcelable
