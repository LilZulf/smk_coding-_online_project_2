package com.github.lilzulf.masaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lilzulf.masaya.Adapter.GrateAdapeter2
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.DataGrate
import com.github.lilzulf.masaya.Object.ResponseGrateList
import com.github.lilzulf.masaya.Util.SharedPreferences
import kotlinx.android.synthetic.main.activity_grateful_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GratefulHistory : AppCompatActivity() {
    var data : SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grateful_history)
        data = SharedPreferences(applicationContext!!)
        iv_close.setOnClickListener {
            finish()
        }
        getGrate()

    }
    private fun getGrate(){
        var addAPI = ServiceRequest.get().getGrate(
            data!!.getString("ID_USER").toString()
        )

        addAPI.enqueue(object : Callback<ResponseGrateList> {
            override fun onFailure(call: Call<ResponseGrateList>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseGrateList>, response: Response<ResponseGrateList>) {
                if(response.body()!!.code == 200){
                    tampilGrate(response.body()!!.data!!)
                }

            }

        })
    }
    private fun tampilGrate(githubUsers: List<DataGrate>) {
        rv_listHistory.layoutManager = LinearLayoutManager(applicationContext)
        rv_listHistory.adapter = GrateAdapeter2(applicationContext, githubUsers) {
        }
    }
}
