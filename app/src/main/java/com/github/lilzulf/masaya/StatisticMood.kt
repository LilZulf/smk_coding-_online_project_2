package com.github.lilzulf.masaya

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.MoodDetail
import com.github.lilzulf.masaya.Object.MoodResponse
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import kotlinx.android.synthetic.main.activity_statistic_mood.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatisticMood : AppCompatActivity() {
    private var data : SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic_mood)
        data = SharedPreferences(applicationContext!!)
        getMood()
        iv_close.setOnClickListener {
            finish()
        }
    }
    private fun getMood(){

        var addAPI = ServiceRequest.get().getMoodDetail(
            data!!.getString("ID_USER").toString()
        )

        addAPI.enqueue(object : Callback<MoodDetail> {
            override fun onFailure(call: Call<MoodDetail>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MoodDetail>, response: Response<MoodDetail>) {
                if(response.body()!!.code == 200){
                    tampilToast(applicationContext,response.body()!!.message.toString())
                    menyenangkan.text = response.body()!!.data!!.GreatCount
                    baik.text = response.body()!!.data!!.GoodCount
                    biasa.text = response.body()!!.data!!.NormalCount
                    buruk.text = response.body()!!.data!!.BadCount
                    menyedihkan.text = response.body()!!.data!!.SadCount
                }
                else{
                    Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_SHORT).show()
                }

            }

        })
    }
}
