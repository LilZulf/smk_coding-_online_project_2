package com.github.lilzulf.masaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.ResponseAuth
import com.github.lilzulf.masaya.Util.SharedPreferences
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private var data : SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        data = SharedPreferences(applicationContext)
        iv_close.setOnClickListener {
            finish()
        }
        btRegister.setOnClickListener{
            registerHandler()
        }
    }
    private fun registerHandler(){
        val email = etEmail.text.toString().trim()
        val pass = etPassword.text.toString().trim()
        val username = etUsername.text.toString().trim()
        val nickname = etNickname.text.toString().trim()

        if (email.isEmpty()){
            etEmail.error = "Harap isi alamat email"
            etEmail.requestFocus()
        }
        if (username.isEmpty()){
            etUsername.error = "Harap isi nama lengkap"
            etUsername.requestFocus()
        }
        if (nickname.isEmpty()){
            etNickname.error = "Harap isi nama panggilan"
            etNickname.requestFocus()
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
            doRegister()
        }
    }
    private fun doRegister(){
        var registAPI = ServiceRequest.get().doRegister(
            etUsername.text.toString(),
            etNickname.text.toString(),
            etEmail.text.toString(),
            etPassword.text.toString()
        )

        registAPI.enqueue(object : Callback<ResponseAuth> {
            override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
                if(response.body()!!.code == 200){
                    Toast.makeText(applicationContext,"Berhasil daftar, silahkan login", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@RegisterActivity,LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_SHORT).show()
                }

            }

        })
    }
}
