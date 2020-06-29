package com.github.lilzulf.masaya

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.Test
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashScreen : AppCompatActivity() {

    private var data : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        data = SharedPreferences(applicationContext!!)
        //doLogin()
        val animFadeIn: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        tvLogo.startAnimation(animFadeIn)
        val cm = this@SplashScreen.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected){
            data!!.setString("MODE","ONLINE")
            intentTo()
        }else{
            data!!.setString("MODE","OFFLINE")
            tampilToast(this,"Mode Offline")
            intentTo()
        }

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

        }, 2000)
    }
}
