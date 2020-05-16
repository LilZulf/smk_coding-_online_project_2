package com.github.lilzulf.masaya

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.ResponseAuth
import com.github.lilzulf.masaya.Object.ResponseGrate
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import kotlinx.android.synthetic.main.activity_grateful.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GratefulActivity : AppCompatActivity() {
    var data : SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grateful)
        data = SharedPreferences(applicationContext!!)
        btSand.setOnClickListener {
            insertHandler()
        }
    }
    private fun insertHandler(){
        val main = etMain.text.toString()

        if(main.isNotEmpty()){
            doInsert(main,false)
        }else{
            etMain.error = "Harap isi formulir ini"
            etMain.requestFocus()
        }
    }
    private fun doInsert(params : String?,isSecond :Boolean = false){
        val intentData = intent.extras
        val date = intentData!!.getString("date")
        var addAPI = ServiceRequest.get().addGrate(
            data!!.getString("ID_USER").toString(),
            params.toString(),
            date.toString()
        )

        addAPI.enqueue(object : Callback<ResponseGrate> {
            override fun onFailure(call: Call<ResponseGrate>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

            override fun onResponse(call: Call<ResponseGrate>, response: Response<ResponseGrate>) {
                if(response.body()!!.code == 200){
                    tampilToast(applicationContext,response.body()!!.message.toString())
//                    if(isSecond == false){
//                        recursive()
//                    }else{
//                        val op1 =etOptional1.text.toString()
//                        val op2 =etOptioanl2.text.toString()
//                        if(op1 == response.body()!!.data!!.text || op2 == response.body()!!.data!!.text  ){
//                            if (){}
//                            recursive(true)
//                        }
//
//                    }
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }

            }

        })
    }
    private fun recursive(isEnd : Boolean = false){
        val op1  = etOptional1.text.toString()
        val op2 = etOptioanl2.text.toString()

        etMain.text.clear()

        if(op1.isNotEmpty()){
            doInsert(op1,true)
        }
        if(op2.isNotEmpty()){
            doInsert(op2,false)
        }
        if(isEnd == true){
            setResult(Activity.RESULT_OK)
            finish()
        }
        else{
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
