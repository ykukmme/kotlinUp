package fastcampus.aop.tinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    private val emailEditText: EditText by lazy {
        findViewById(R.id.emailEditText)
    }

    private val passwordEditText: EditText by lazy {
        findViewById(R.id.passwordEditText)
    }

    private val loginButton: Button by lazy {
        findViewById(R.id.loginButton)
    }

    private val signUpButton: Button by lazy {
        findViewById(R.id.signUpButton)
    }

    private val facebookLoginButton: LoginButton by lazy {
        findViewById(R.id.facebookLoginButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()

        initLoginButton()
        initSignUpButton()
        initEmailAndPasswordEditText()
        initFacebookLoginButton()

    }

    private fun initLoginButton() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        finish()
                    } else {
                        Toast.makeText(this, "???????????? ??????????????????. ??????????????? ??????????????? ??????????????????.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

    }

    private fun initSignUpButton() {
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "??????????????? ??????????????????. ????????? ????????? ???????????????.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "?????? ????????? ??????????????????, ??????????????? ??????????????????.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    private fun initFacebookLoginButton() {
        facebookLoginButton.setPermissions("email", "public_profile")
        facebookLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    //????????? ??????
                    val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener(this@Login) { task ->
                            if (task.isSuccessful) {
                                finish()
                            } else {
                                Toast.makeText(this@Login, "???????????? ??????????????????", Toast.LENGTH_SHORT).show()
                            }
                        }
                }

                override fun onCancel() {
                    //????????? ??????
                    Toast.makeText(this@Login, "???????????? ?????????????????????", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    //??????
                    Toast.makeText(this@Login, "???????????? ??????????????????", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun initEmailAndPasswordEditText() {
        emailEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable

        }

        passwordEditText.addTextChangedListener {
            val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled = enable
            signUpButton.isEnabled = enable

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}