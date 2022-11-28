package fastcampus.aop.old_sell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fastcampus.aop.old_sell.chat.ChatFragment
import fastcampus.aop.old_sell.home.HomeFragment
import fastcampus.aop.old_sell.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigationView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val chatFragment = ChatFragment()
        val myPageFragment = MyPageFragment()

        replaceFragment(homeFragment)

        //네비게이션 바에 아이템이 선택되었을때
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatList -> replaceFragment(chatFragment)
                R.id.myPage -> replaceFragment(myPageFragment)

            }
            true
        }
    }

    //프래그먼트 전환 기능 함수
    private fun replaceFragment(fragment: Fragment) {
        //입력 받은 프래그먼트로 전환하게 하는 메소드
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }
}