package com.blueview.led.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blueview.led.MainActivity
import com.blueview.led.R


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var username:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var loading:ProgressBar
    private val list:ArrayList<String>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bar_color)); //设置状态栏颜色（底色），
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为黑色,看其他文章，说只有黑色和白色
        }
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        list?.add("hjsdhfd")
        if (list===null){
            Toast.makeText(this,"",Toast.LENGTH_LONG);
        }
        print("test")
        //textview=findViewById(R.id.textBottom)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        loading = findViewById(R.id.loading)
        loginViewModel=ViewModelProvider(this,LoginViewModelFactory())[LoginViewModel::class.java]
        this.loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
//        textview=findViewById(R.id.textBottom)
//        textview.setOnClickListener {
//            Toast.makeText(this,"test",Toast.LENGTH_LONG)
//        }
        loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                val intent=Intent()
                intent.setClass(this,MainActivity::class.java)
                startActivity(intent)
            }
            setResult(Activity.RESULT_OK)
            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }

        }

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$welcome $displayName",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

}