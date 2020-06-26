package com.github.lilzulf.masaya


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.MoodDetail
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
       // getMood()
        chart()
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
    private fun chart(){
        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("John", 10000))
        data.add(ValueDataEntry("Jake", 12000))
        data.add(ValueDataEntry("Peter", 18000))

        pie.data(data)

        val anyChartView = findViewById(R.id.ChartMood) as AnyChartView
        anyChartView.setChart(pie)
    }
}
