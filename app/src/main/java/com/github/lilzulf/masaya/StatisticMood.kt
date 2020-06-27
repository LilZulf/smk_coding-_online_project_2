package com.github.lilzulf.masaya


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.MoodDetail
import com.github.lilzulf.masaya.Object.MyMoodModel
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_statistic_mood.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext


class StatisticMood : AppCompatActivity() {
    private var data : SharedPreferences? = null
    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth
    private var _menyedihkan : Int = 0
    private var _buruk : Int = 0
    private var _biasa : Int = 0
    private var _baik : Int = 0
    private var _menyenangkan : Int = 0
    private val TAG = "Statistik Mood"
    private val state = arrayOf(0,0,0,0,0)
    //private var statee = ArrayList<>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic_mood)
        data = SharedPreferences(applicationContext!!)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference()
       // getMood()
        getMenyedihkan()
        getBuruk()
        getOK()
        getBaik()
        getMenyenangkan()
        iv_close.setOnClickListener {
            finish()
        }
    }
    private fun chart(){
        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()

        data.add(ValueDataEntry("Menyedihkan", _menyedihkan))
        data.add(ValueDataEntry("Buruk", _buruk))
        data.add(ValueDataEntry("Ok", _biasa))
        data.add(ValueDataEntry("Baik", _baik))
        data.add(ValueDataEntry("Menyenangkan", _menyenangkan))

        pie.data(data)
        pie.title("Statistik Mood")

        val anyChartView = findViewById(R.id.ChartMood) as AnyChartView
        anyChartView.setChart(pie)
    }
    private fun getMenyedihkan(){

        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref
            .child(getUserID)
            .child("Mood")
            .orderByChild("state")
            .equalTo("0")
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@StatisticMood, "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    //dataTarget = java.util.ArrayList<MyMoodModel>()
                    state.set(0,total.toInt())
                    menyedihkan.text = total
                    dataSetter("0",total.toInt())

                }
            })
    }
    private fun getBuruk(){

        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref
            .child(getUserID)
            .child("Mood")
            .orderByChild("state")
            .equalTo("1")
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@StatisticMood, "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    //dataTarget = java.util.ArrayList<MyMoodModel>()
                    state.set(1,total.toInt())
                    buruk.text = total
                    Log.d(TAG,total)
                    dataSetter("1",total.toInt())
                }
            })
    }
    private fun getOK(){

        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref
            .child(getUserID)
            .child("Mood")
            .orderByChild("state")
            .equalTo("2")
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@StatisticMood, "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    //dataTarget = java.util.ArrayList<MyMoodModel>()
                    state.set(2,total.toInt())
                    biasa.text = total
                    Log.d(TAG,total)
                    dataSetter("2",total.toInt())
                }
            })
    }
    private fun getBaik(){

        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref
            .child(getUserID)
            .child("Mood")
            .orderByChild("state")
            .equalTo("3")
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@StatisticMood, "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    //dataTarget = java.util.ArrayList<MyMoodModel>()
                    state.set(3,total.toInt())
                    baik.text = total
                    dataSetter("3",total.toInt())
                    Log.d(TAG,total)
                }
            })
    }
    private fun getMenyenangkan(){

        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref
            .child(getUserID)
            .child("Mood")
            .orderByChild("state")
            .equalTo("4")
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@StatisticMood, "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    //dataTarget = java.util.ArrayList<MyMoodModel>()
                    Log.d(TAG,total)
                    menyenangkan.text = total
                    dataSetter("4",total.toInt())
                    chart()
                }

            })
       // chart()
    }
    private fun dataSetter(data: String,num: Int){
        if(data == "0"){
            _menyedihkan = num
        }
        if(data == "1"){
            _buruk = num
        }
        if(data == "2"){
            _biasa = num
        }
        if(data == "3"){
            _baik = num
        }
        if(data == "4"){
            _menyenangkan = num
        }else{
           //chart()
        }
    }

}
