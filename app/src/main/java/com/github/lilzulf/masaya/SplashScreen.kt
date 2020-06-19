package com.github.lilzulf.masaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.ResponseAuth
import com.github.lilzulf.masaya.Object.Test
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class SplashScreen : AppCompatActivity() {

    private var data : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        data = SharedPreferences(applicationContext!!)
        doLogin()
    }
    private fun doLogin(){
        var registAPI = ServiceRequest.get().test(
            "Bismillah"
        )

        registAPI.enqueue(object : Callback<Test> {
            override fun onFailure(call: Call<Test>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                Log.d("ERROR",t.message)
            }

            override fun onResponse(call: Call<Test>, response: Response<Test>) {
                if(response.body()?.code == 200){
                    intentTo()
                }
                else{
                    tampilToast(this@SplashScreen,"Maaf Server Down")
                }
            }

        })
    }
    private fun intentTo(){
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

        }, 1000)
    }
}
