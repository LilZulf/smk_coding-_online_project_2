package com.github.lilzulf.masaya

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lilzulf.masaya.Adapter.GrateAdapeter1
import com.github.lilzulf.masaya.Adapter.GrateAdapeter3
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.*
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_grateful.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GratefulActivity : AppCompatActivity() {
    var data : SharedPreferences? = null
    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth
    lateinit var dataTarget : ArrayList<MyGrateModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grateful)
        data = SharedPreferences(applicationContext!!)
        //getGrate()
        getData()
        btSand.setOnClickListener {
            insertHandler()
        }
    }
    private fun insertHandler(){
        val main = etMain.text.toString()

        if(main.isNotEmpty()){
           // doInsert(main,false)
            GrateFirebase()
        }else{
            etMain.error = "Harap isi formulir ini"
            etMain.requestFocus()
        }
    }
//    private fun doInsert(params : String?,isSecond :Boolean = false){
//        val intentData = intent.extras
//        val date = intentData!!.getString("date")
//        var addAPI = ServiceRequest.get().addGrate(
//            data!!.getString("ID_USER").toString(),
//            params.toString(),
//            date.toString()
//        )
//
//        addAPI.enqueue(object : Callback<ResponseGrate> {
//            override fun onFailure(call: Call<ResponseGrate>, t: Throwable) {
//                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
//                setResult(Activity.RESULT_CANCELED)
//                finish()
//            }
//
//            override fun onResponse(call: Call<ResponseGrate>, response: Response<ResponseGrate>) {
//                if(response.body()!!.code == 200){
//                    tampilToast(applicationContext,response.body()!!.message.toString())
////                    if(isSecond == false){
////                        recursive()
////                    }else{
////                        val op1 =etOptional1.text.toString()
////                        val op2 =etOptioanl2.text.toString()
////                        if(op1 == response.body()!!.data!!.text || op2 == response.body()!!.data!!.text  ){
////                            if (){}
////                            recursive(true)
////                        }
////
////                    }
//                    setResult(Activity.RESULT_OK)
//                    finish()
//                }
//                else{
//                    Toast.makeText(applicationContext,response.body()!!.message, Toast.LENGTH_SHORT).show()
//                    setResult(Activity.RESULT_CANCELED)
//                    finish()
//                }
//
//            }
//
//        })
//    }
//    private fun getGrate(){
//        val intentData = intent.extras
//        val date = intentData!!.getString("date")
//        var addAPI = ServiceRequest.get().getGrateByDate(
//            data!!.getString("ID_USER").toString(),
//            date.toString()
//        )
//
//        addAPI.enqueue(object : Callback<ResponseGrateList> {
//            override fun onFailure(call: Call<ResponseGrateList>, t: Throwable) {
//                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<ResponseGrateList>, response: Response<ResponseGrateList>) {
//                if(response.body()!!.code == 200){
//                    tampilGrate(response.body()!!.data!!)
//                }
//
//            }
//
//        })
//    }
//    private fun tampilGrate(githubUsers: List<DataGrate>) {
//        rcGrate.layoutManager = LinearLayoutManager(applicationContext)
//        rcGrate.adapter = GrateAdapeter1(applicationContext, githubUsers) {
//        }
//    }
    private fun getData() {
        //Mendapatkan Referensi Database
        val intentData = intent.extras
        val date = intentData!!.getString("date")
        auth = FirebaseAuth.getInstance()
        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref = FirebaseDatabase.getInstance().getReference()
        ref
            .child(getUserID)
            .child( "Grate" )
            .orderByChild("date")
            .equalTo(date)
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@GratefulActivity, "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //Inisialisasi ArrayList
                    dataTarget = java.util.ArrayList<MyGrateModel>()
                    for (snapshot in dataSnapshot. children ) {
                        //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                        val target = snapshot.getValue(MyGrateModel:: class . java )
                        //Mengambil Primary Key, digunakan untuk proses Update dan
                        target?.key = snapshot.key.toString()
                        dataTarget.add(target!!)
                    }
                    //Memasang Adapter pada RecyclerView
                    rcGrate.layoutManager = LinearLayoutManager(this@GratefulActivity)
                    rcGrate.adapter = GrateAdapeter3(this@GratefulActivity, dataTarget) {
                    }
                }
            })
    }
    private fun GrateFirebase(){
        val intentData = intent.extras
        val year = intentData!!.getString("date").toString()
        val title = etMain.text.toString()
        val user_id = auth!!.currentUser!!.uid.toString()

        val target = MyGrateModel(title, year,null)
        ref .child(user_id).child( "Grate" ).push().setValue(target).addOnCompleteListener {
            Toast.makeText( this , "Data Berhasil Disimpan" ,
                Toast. LENGTH_SHORT ).show()
        }
        setResult(Activity.RESULT_OK)
        finish()

    }
}
