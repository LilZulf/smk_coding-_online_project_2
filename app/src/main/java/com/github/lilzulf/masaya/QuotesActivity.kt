package com.github.lilzulf.masaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.QuotesResponse
import kotlinx.android.synthetic.main.activity_quotes.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class QuotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
        doQuote()
        parentQuote.visibility = View.GONE
        btQuote.setOnClickListener {
            doQuote()
        }
        iv_close.setOnClickListener {
            finish()
        }
    }
    private fun doQuote(){
        var registAPI = ServiceRequest.get().getQuotes()

        registAPI.enqueue(object : Callback<QuotesResponse> {
            override fun onFailure(call: Call<QuotesResponse>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                Log.d("ERROR",t.message)
            }

            override fun onResponse(call: Call<QuotesResponse>, response: Response<QuotesResponse>) {
                tvQuote.text = response.body()!!.content.toString()
                tvName.text = "- "+response.body()!!.author.toString()
                parentQuote.visibility = View.VISIBLE
                //Log.d("Data",response.body()!!.toString())
            }

        })
    }
}
