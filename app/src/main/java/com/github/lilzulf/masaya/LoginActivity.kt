package com.github.lilzulf.masaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.ResponseAuth
import com.github.lilzulf.masaya.Util.SharedPreferences
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var data : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        data = SharedPreferences(applicationContext!!)
        btLogin.setOnClickListener {
            loginHandler()
        }
        tvRegister.setOnClickListener {
            val i = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(i)
        }
    }
    private fun loginHandler(){
        val email = etEmail.text.toString().trim()
        val pass = etPassword.text.toString().trim()

        if (email.isEmpty()){
            etEmail.error = "Harap isi alamat email"
            etEmail.requestFocus()
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.error = "Harap masukkan email yang valid"
            etEmail.requestFocus()
        }

        if (pass.isEmpty()){
            etPassword.error = "Harap masukkan kata sandi"
            etPassword.requestFocus()
        }
        else {
            doLogin()
        }
    }
    private fun doLogin(){
        var registAPI = ServiceRequest.get().doLogin(
            etEmail.text.toString(),
            etPassword.text.toString()
        )

        registAPI.enqueue(object : Callback<ResponseAuth> {
            override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                if(response.body()!!.code == 200){
                    data!!.setString("ID_USER",response.body()!!.data!!.id_user.toString())
                    data!!.setString("USERNAME",response.body()!!.data!!.username.toString())
                    data!!.setString("NICKNAME",response.body()!!.data!!.nickname.toString())
                    data!!.setString("EMAIL",response.body()!!.data!!.email.toString())
                    data!!.session("LOGIN",true)
                    toMain()
                }
                else{
                    Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_SHORT).show()
                }

            }

        })
    }
    private fun toMain(){
        val i = Intent(this@LoginActivity,MainActivity::class.java)
        Toast.makeText(applicationContext,"Berhasil login", Toast.LENGTH_SHORT).show()
        startActivity(i)
        finish()
    }
}
