package fastcampus.aop.book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import fastcampus.aop.book.adapter.BookAdapter
import fastcampus.aop.book.adapter.HistoryAdapter
import fastcampus.aop.book.api.BookService
import fastcampus.aop.book.databinding.ActivityMainBinding
import fastcampus.aop.book.model.History
import fastcampus.aop.book.model.SearchBook
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var historyadapter: HistoryAdapter
    private lateinit var bookService: BookService
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initBookRecyclerView()
        initHistoryRecyclerView()
        initSearchEditText()

        db = getAppDatabase(this)

        //retrofit을 사용하기 위해 객체를 만듬. Gson으로 변환하기에 .addConverterFactory(GsonConverterFactory.create())를 추가함
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun search(keyword: String) {

        bookService.getBooksByName(
            getString(R.string.Naver_api_id),
            getString(R.string.Naver_api_secretKey),
            keyword
        )
            .enqueue(object : Callback<SearchBook> {
                override fun onResponse(call: Call<SearchBook>, response: Response<SearchBook>) {
                    //검색 성공

                    hideHistory()
                    saveSearchKeyword(keyword)

                    if (response.isSuccessful.not()) {
                        return
                    }

                    adapter.submitList(response.body()?.books.orEmpty())
                }

                override fun onFailure(call: Call<SearchBook>, t: Throwable) {
                    //검색 실패
                    hideHistory()
                }

            })
    }

    private fun initBookRecyclerView() {
        adapter = BookAdapter(itemClickedListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("bookModel", it)
            startActivity(intent)
        })

        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }

    private fun initHistoryRecyclerView() {
        historyadapter = HistoryAdapter(historyDeleteClickedListener = {
            deleteSearchKeyword(it)
        })

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyadapter
        initSearchEditText()
    }

    private fun initSearchEditText() {
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.searchEditText.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showHistory()
            }
            return@setOnTouchListener false
        }
    }

    private fun showHistory() {
        Thread {
            val history = db.historyDao().getAll().reversed()

            runOnUiThread {
                binding.historyRecyclerView.isVisible = true
                historyadapter.submitList(history.orEmpty())
            }
        }.start()

        binding.historyRecyclerView.isVisible = true
    }

    private fun hideHistory() {
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().insertHistory(History(null, keyword))
        }.start()
    }

    private fun deleteSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().delete(keyword)
            showHistory()
        }.start()
    }
}