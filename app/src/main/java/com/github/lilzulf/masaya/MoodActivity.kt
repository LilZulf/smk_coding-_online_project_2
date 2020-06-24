package com.github.lilzulf.masaya

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.MoodResponse
import com.github.lilzulf.masaya.Object.MyMoodModel
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_mood.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoodActivity : AppCompatActivity() {

    private var data : SharedPreferences? = null
    var isChange : Boolean = false
    lateinit var ref : DatabaseReference
    private var auth : FirebaseAuth? = null
    val Mood = arrayOf("Menyedihkan", "Buruk", "Hmmm Ok", "Baik dong","Menyenangkan!")
    val iconsMood = arrayOf(R.drawable.ic_sentiment_very_dissatisfied,R.drawable.ic_sentiment_dissatisfied,
        R.drawable.ic_sentiment,R.drawable.ic_mood,R.drawable.ic_sentiment_very_satisfied
        )
    var state : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)
        data = SharedPreferences(applicationContext!!)
        ref = FirebaseDatabase.getInstance().getReference()
        auth = FirebaseAuth.getInstance()
        // Set a SeekBar change listener
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                tv_desc.text = Mood[i]
                ivIcon.setBackgroundResource(iconsMood[i])
                state = i
                isChange = true
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something

            }
        })
        btDone.setOnClickListener {
            if(isChange){
                addMoodFirebase()
            }else{
                tampilToast(this,"Mohon untuk menggeser bar")
            }
        }
    }
    private fun addMood(){
        val intentData = intent.extras
        val date = intentData!!.getString("date")
        var addAPI = ServiceRequest.get().addMood(
            state.toString(),
            date.toString(),
            data!!.getString("ID_USER").toString()

        )

        addAPI.enqueue(object : Callback<MoodResponse> {
            override fun onFailure(call: Call<MoodResponse>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MoodResponse>, response: Response<MoodResponse>) {
                if(response.body()!!.code == 200){
                    tampilToast(applicationContext,response.body()!!.message.toString())
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
    private fun addMoodFirebase(){
        val intentData = intent.extras
        val date = intentData!!.getString("date").toString()
        val user_id = auth!!.currentUser!!.uid.toString()

        val target = MyMoodModel(state.toString(), date,null)
        ref .child(user_id).child( "Mood" ).push().setValue(target).addOnCompleteListener {
            Toast.makeText( this , "Data Berhasil Disimpan" ,
                Toast. LENGTH_SHORT ).show()
        }
        setResult(Activity.RESULT_OK)
        finish()
    }
}
