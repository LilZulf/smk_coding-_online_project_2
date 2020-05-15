package com.github.lilzulf.masaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.github.lilzulf.masaya.Util.SharedPreferences

class SplashScreen : AppCompatActivity() {

    private var data : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        data = SharedPreferences(applicationContext!!)

        val handler = Handler()
        handler.postDelayed({
            if(data!!.getSession("LOGIN")){
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
//            val intent = Intent(this@SplashScreen, MainActivity::class.java)
//                startActivity(intent)
//                finish()

        }, 3000)
    }
}
