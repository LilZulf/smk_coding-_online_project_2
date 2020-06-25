package com.github.lilzulf.masaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lilzulf.masaya.Adapter.GrateAdapeter1
import com.github.lilzulf.masaya.Adapter.GrateAdapeter2
import com.github.lilzulf.masaya.Adapter.GrateAdapeter3
import com.github.lilzulf.masaya.Data.ServiceRequest
import com.github.lilzulf.masaya.Object.DataGrate
import com.github.lilzulf.masaya.Object.MyGrateModel
import com.github.lilzulf.masaya.Object.ResponseGrateList
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_grateful.*
import kotlinx.android.synthetic.main.activity_grateful_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GratefulHistory : AppCompatActivity() {
    var data : SharedPreferences? = null
    lateinit var ref : DatabaseReference
    lateinit var auth : FirebaseAuth
    lateinit var dataTarget : ArrayList<MyGrateModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grateful_history)
        data = SharedPreferences(applicationContext!!)
        iv_close.setOnClickListener {
            finish()
        }
        //getGrate()
        getData()

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
    private fun getData() {
        //Mendapatkan Referensi Database
//        val intentData = intent.extras
//        val date = intentData!!.getString("date")
        auth = FirebaseAuth.getInstance()
        val getUserID: String = auth.getCurrentUser()?.getUid(). toString ()
        ref = FirebaseDatabase.getInstance().getReference()
        ref
            .child(getUserID)
            .child( "Grate" )
            .orderByChild("date")
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@GratefulHistory, "Database Error yaa..." ,
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
                    rv_listHistory.layoutManager = LinearLayoutManager(this@GratefulHistory)
                    rv_listHistory.adapter = GrateAdapeter1(this@GratefulHistory, dataTarget) {
                    }
                }
            })
    }
}
