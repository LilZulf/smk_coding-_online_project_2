package com.github.lilzulf.masaya.Fragment


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.GratefulActivity
import com.github.lilzulf.masaya.MoodActivity
import com.github.lilzulf.masaya.Object.*
import com.github.lilzulf.masaya.R
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.dismissLoading
import com.github.lilzulf.masaya.Util.showLoading
import com.github.lilzulf.masaya.Util.tampilToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_diary.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment() {
    private var data: SharedPreferences? = null
    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth
    lateinit var dataTarget2 : ArrayList<MyGrateModel>
    lateinit var dataTarget : ArrayList<MyMoodModel>
    var cMin = Calendar.getInstance()
    val iconsMood = arrayOf(R.drawable.ic_sentiment_very_dissatisfied,R.drawable.ic_sentiment_dissatisfied,
        R.drawable.ic_sentiment,R.drawable.ic_mood,R.drawable.ic_sentiment_very_satisfied
    )
    val Mood = arrayOf("Menyedihkan", "Buruk", "Hmmm Ok", "Baik dong","Menyenangkan!")
    companion object {
        val REQUEST_CODE = 101
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = SharedPreferences(activity!!)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference()
        llParent.visibility = View.GONE
        setGreetings()

        btDate.setOnClickListener {
           openDatePicker()
        }
        rl_mood2.setOnClickListener {
            tampilToast(activity!!,"Kamu sudah ceritakan hari ini")
        }
        rl_grate2.setOnClickListener {
            navigasiAddGrate()
        }
        rl_grate1.setOnClickListener {
            navigasiAddGrate()
        }
    }
    private fun openDatePicker(){
        val sdf = SimpleDateFormat("dd/M/yyyy")
        var thisAYear = cMin.get(Calendar.YEAR)
        var thisAMonth = cMin.get(Calendar.MONTH)
        var thisADay = cMin.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this.requireActivity(), DatePickerDialog.OnDateSetListener { view2, thisYear, thisMonth, thisDay ->
            thisAYear = thisYear
            thisAMonth = thisMonth
            thisADay = thisDay
            cMin.set(thisAYear,thisAMonth,thisADay)
            //getMood(sdf.format(cMin.time))
            getMoodFirebase(sdf.format(cMin.time))
            btDate.text = sdf.format(cMin.time)
        }, thisAYear, thisAMonth, thisADay)
        dpd.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dpd.show()

    }
    private fun setGreetings(){
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        btDate.text = currentDate.toString()
        if (timeOfDay >= 0 && timeOfDay < 12) {
            Toast.makeText(activity!!, "Selamat Pagi", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat pagi, "+"\n"+getString(R.string.moring)
//            tvGreeting2.text = "Tuliskan dalam hatimu bahwa setiap hari adalah hari terbaik dalam setahun."
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Toast.makeText(activity!!, "Selamat Siang", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat siang, "+"\n"+getString(R.string.noon)
//            tvGreeting2.text = "Jalani harimu dengan berkah dan cinta."
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Toast.makeText(activity!!, "Selamat Sore", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat sore, "+"\n"+getString(R.string.evening)
//            tvGreeting2.text = "Ada yang tak tenggelam ketika senja datang, yakni Rasa."
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            Toast.makeText(activity!!, "Selamat malam", Toast.LENGTH_SHORT).show()
            tvGreeting.text = "Selamat malam, "+"\n"+getString(R.string.night)
//            tvGreeting2.text = "Waktunya padamkan bara setelah lelah bekerja."
        }
        //getMood(currentDate)
        //getGrateFirebase(currentDate)
        getMoodFirebase(currentDate)
    }
    private fun getMood(date : String?){
        showLoading(activity!!, swipeRefreshLayout)
        var addAPI = ServiceRequest.get().getMood(
            date.toString(),
            data!!.getString("ID_USER").toString()
        )

        addAPI.enqueue(object : Callback<MoodResponse> {
            override fun onFailure(call: Call<MoodResponse>, t: Throwable) {
                Toast.makeText(activity!!,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MoodResponse>, response: Response<MoodResponse>) {
                if(response.body()?.code == 200){
                    rl_mood2.visibility = View.VISIBLE
                    rl_mood_1.visibility = View.GONE
                    iv_edit2_.setImageResource(iconsMood[response.body()!!.data!!.state!!.toInt()])
                    tvCardTitle2.text = "Hariku terasa"
                    tvCardDesc2.visibility = View.VISIBLE
                    tvCardDesc2.text = Mood[response.body()!!.data!!.state!!.toInt()]
                    getGrate(date)
                }
                else{
                    tampilToast(activity!!,"Ayo ceritakan harimu !")
                    rl_mood2.visibility = View.GONE
                    rl_mood_1.visibility = View.VISIBLE
                    rl_mood_1.setOnClickListener {
                        navigasiAddMood()
                    }
                    getGrate(date)
                }
               
            }

        })
    }
    private fun getGrate(date : String?){
        var addAPI = ServiceRequest.get().getGrateTotal(
            data!!.getString("ID_USER").toString(),
            date.toString()
        )

        addAPI.enqueue(object : Callback<MoodDetail> {
            override fun onFailure(call: Call<MoodDetail>, t: Throwable) {
                Toast.makeText(activity!!,t.message, Toast.LENGTH_LONG).show()
                llParent.visibility = View.VISIBLE
                dismissLoading(swipeRefreshLayout)
            }

            override fun onResponse(call: Call<MoodDetail>, response: Response<MoodDetail>) {
                if(response.body()!!.data!!.total != "0"){
                    rl_grate1.visibility = View.GONE
                    rl_grate2.visibility = View.VISIBLE
                    tv_edit3.text = response.body()!!.data!!.total.toString()
                    llParent.visibility = View.VISIBLE
                    dismissLoading(swipeRefreshLayout)
                }
                else{
                    rl_grate2.visibility = View.GONE
                    rl_grate1.visibility = View.VISIBLE
                    llParent.visibility = View.VISIBLE
                    dismissLoading(swipeRefreshLayout)
                }

            }

        })
    }
    private fun navigasiAddMood(){
        val intent = Intent(activity!!, MoodActivity::class.java)
        intent.putExtra("date",btDate.text.toString())
        startActivityForResult(intent, REQUEST_CODE)
    }
    private fun navigasiAddGrate(){
        val intent = Intent(activity!!, GratefulActivity::class.java)
        intent.putExtra("date",btDate.text.toString())
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                setGreetings()
            }else{
                tampilToast(activity!!,"Tidak jadi")
            }
        }
    }
    private fun getMoodFirebase(date: String?){
        showLoading(activity!!,swipeRefreshLayout)
        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref
            .child(getUserID)
            .child("Mood")
            .orderByChild("date")
            .equalTo(date)
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    dismissLoading(swipeRefreshLayout)
                    Toast.makeText(getContext(), "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    dataTarget = java.util.ArrayList<MyMoodModel>()

                    if(total == "0"){
                        tampilToast(activity!!,"Ayo ceritakan harimu !")
                        rl_mood2.visibility = View.GONE
                        rl_mood_1.visibility = View.VISIBLE
                        rl_mood_1.setOnClickListener {
                            navigasiAddMood()
                        }
                        getGrateFirebase(date)
                    }else{
                        rl_mood2.visibility = View.VISIBLE
                        rl_mood_1.visibility = View.GONE
                        tvCardTitle2.text = "Hariku terasa"
                        tvCardDesc2.visibility = View.VISIBLE
                        tvCardDesc2.text = Mood[total.toInt()]
                        getGrateFirebase(date)
                        for (snapshot in dataSnapshot. children ) {
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            val target = snapshot.getValue(MyMoodModel:: class . java )
                            //Mengambil Primary Key, digunakan untuk proses Update dan
                            iv_edit2_.setImageResource(iconsMood[target!!.state.toInt()])
                            tvCardDesc2.text = Mood[target!!.state.toInt()]
                        }
                    }

                }
            })
    }

    private fun getGrateFirebase(date : String?){
        showLoading(activity!!, swipeRefreshLayout)

        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()

        ref
            .child(getUserID)
            .child( "Grate" )
            .orderByChild("date")
            .equalTo(date)
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    dismissLoading(swipeRefreshLayout)
                    Toast.makeText(getContext(), "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    if(total == "0"){
                        rl_grate2.visibility = View.GONE
                        rl_grate1.visibility = View.VISIBLE
                        llParent.visibility = View.VISIBLE
                        dismissLoading(swipeRefreshLayout)
                    }else{
                        rl_grate1.visibility = View.GONE
                        rl_grate2.visibility = View.VISIBLE
                        tv_edit3.text = total
                        llParent.visibility = View.VISIBLE
                        dismissLoading(swipeRefreshLayout)
                    }

                }
            })
    }

}
