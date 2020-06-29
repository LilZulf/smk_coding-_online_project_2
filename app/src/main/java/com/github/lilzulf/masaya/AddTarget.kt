package com.github.lilzulf.masaya

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.Object.ResponseAuth
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import com.github.lilzulf.masaya.viewmodel.MyTargetViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_target.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTarget : AppCompatActivity() {
    private var data : SharedPreferences? = null
    lateinit var ref : DatabaseReference
    private var auth : FirebaseAuth? = null
    private val date = "2020"
    private val viewModel by viewModels<MyTargetViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_target)
        data = SharedPreferences(applicationContext!!)
        ref = FirebaseDatabase.getInstance().getReference()
        auth = FirebaseAuth.getInstance()
        viewModel.init(this@AddTarget);
        iv_close.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        btTarget.setOnClickListener {
            val target = etTarget.text.toString().trim()

            if(target.isEmpty()){
                etTarget.error = "Harap isi target anda"
                etTarget.requestFocus()
            }else{
                //addTarget()
                addTargetFirebase()
            }
        }
    }
    private fun addTarget(){
        val intentData = intent.extras
        val year = intentData!!.getString("year")
        var addAPI = ServiceRequest.get().addTarget(
            etTarget.text.toString(),
            year.toString(),
            data!!.getString("ID_USER").toString()
        )

        addAPI.enqueue(object : Callback<ResponseAuth> {
            override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

            override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
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
    private fun addTargetFirebase(){
        val intentData = intent.extras
        val year = intentData!!.getString("year").toString()
        val title = etTarget.text.toString()
        val user_id = auth!!.currentUser!!.uid.toString()

        val target = MyTargetModel(title, year,"")
        ref .child(user_id).child( "Target" ).push().setValue(target).addOnCompleteListener {
            Toast.makeText( this , "Data Berhasil Disimpan" ,
                Toast. LENGTH_SHORT ).show()
            //viewModel.addData(target)
        }
        setResult(Activity.RESULT_OK)
        finish()
    }
}
