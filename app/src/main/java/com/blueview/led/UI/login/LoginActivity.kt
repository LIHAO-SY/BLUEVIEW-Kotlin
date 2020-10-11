package com.blueview.led.UI.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.blueview.led.MainActivity
import com.blueview.led.R


class LoginActivity : AppCompatActivity() {

    private lateinit var username:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var loading:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        setISfullScreen(this,true)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        loading = findViewById(R.id.loading)



        username.afterTextChanged {

        }

        password.apply {
            afterTextChanged {

            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                    {

                    }

                }
                false
            }

            login.setOnClickListener {
                //loading.visibility = View.VISIBLE
                val intent=Intent()
                intent.setClass(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }

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
    fun setISfullScreen(activity: Activity,isFullScreen:Boolean) {
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            )
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if(!isFullScreen)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //getWindow().setStatusBarColor(getResources().getColor(R.color.bar_color)); //设置状态栏颜色（底色），
                getWindow()?.getDecorView()?.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为黑色,看其他文章，说只有黑色和白色
            }
        }
    }

}