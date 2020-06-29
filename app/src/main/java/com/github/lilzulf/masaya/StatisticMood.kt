package com.github.lilzulf.masaya



import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_statistic_mood.*


class StatisticMood : AppCompatActivity() {
    private var data : SharedPreferences? = null
    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth
    private var _menyedihkan : Int? = null
    private var _buruk : Int? = null
    private var _biasa : Int? = null
    private var _baik : Int? = null
    private var _menyenangkan : Int? = null
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
//        getBuruk()
//        getOK()
//        getBaik()
//        getMenyenangkan()
        //sendNotification("Hai")
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
        notifSetting()
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
                    getBuruk()
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
                    getOK()
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
                    getBaik()
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
                    getMenyenangkan()
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
    private fun sendNotification(messageBody: String,title : String,playlist : String) {
        val uri: Uri = Uri.parse(playlist)
        val yt = Intent(Intent.ACTION_VIEW,uri)
        yt.setPackage("com.google.android.youtube")
        yt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, yt,
            PendingIntent.FLAG_ONE_SHOT)

//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = androidx.core.app.NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_emoticons)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    private fun notifSetting(){
        var sad = _menyedihkan!! + _buruk!!
        var happy = _baik!! + _menyenangkan!!
        val handler = Handler()
        if(sad > happy){
            handler.postDelayed({
                sendNotification("Masaya ada rekomendasi playlist buat kamu :)",
                    "Jangan Sedih mending dengerin musik","https://www.youtube.com/watch?v=7k9FBNqGhBg"
                    )
            },4000)
        }
        else if(happy > sad){
            handler.postDelayed({
                sendNotification("Biar tambah semangat masaya ada rekomendasi playlist buat kamu !",
                    "Cieee, yang lagi Good mood","https://www.youtube.com/watch?v=2chfsFTNEXw&t=3536s"
                )
            },4000)
        }



    }

}
