package fastcampus.aop.book.api

import fastcampus.aop.book.model.SearchBook
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookService {

    //위의 헤더들은 api 요청 아이디와 키값. query에는 검색어가 들어가야 한다고 네이버 api에 나와있음
    @GET("/v1/search/book.json")
    fun getBooksByName(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") keyword: String
    ): Call<SearchBook>
}