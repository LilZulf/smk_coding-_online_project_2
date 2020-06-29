package com.github.lilzulf.masaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.github.lilzulf.masaya.Adapter.ViewPagerAdapter
import com.github.lilzulf.masaya.Object.TokenModel
import com.github.lilzulf.masaya.Util.SharedPreferences
import com.github.lilzulf.masaya.Util.tampilToast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val menuTeks = arrayOf("Diary","Pencapaian","Statistik")
    val menuIcons = arrayOf(R.drawable.ic_mood,R.drawable.ic_content,R.drawable.ic_insert_chart)
    var data : SharedPreferences? = null
    lateinit var ref : DatabaseReference
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ViewPagerAdapter(this)
        data = SharedPreferences(applicationContext)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference()
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        checkToken()
        viewPager.setAdapter(adapter)
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy {
                tab, position ->
                    tab.text = menuTeks[position]
                    tab.icon = ResourcesCompat.getDrawable(resources,menuIcons[position], null)
        }).attach()
        ic_more.setOnClickListener {
            popUp()
        }
    }
    private fun checkToken(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Failed : ", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                //addToken(token.toString())
                getTokenFirebase(token.toString())
                Log.d("Msg : ", msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
    }
    private fun popUp(){
        val popupMenu: PopupMenu = PopupMenu(this,ic_more)
        popupMenu.menuInflater.inflate(R.menu.optionmenu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when(item!!.itemId) {
                R.id.profile ->{
//                    val i = Intent(this@MainActivity,AddKategori::class.java)
//                    startActivity(i)
                    tampilToast(this,"Soon")
                }

                R.id.logout ->{
                    val builder = AlertDialog.Builder(this@MainActivity)

                    // Set the alert dialog title
                    builder.setTitle("Logout")

                    // Display a message on alert dialog
                    builder.setMessage("Apakah anda yakin untuk keluar?")

                    // Set a positive button and its click listener on alert dialog
                    builder.setPositiveButton("Iya"){dialog, which ->
                        // Do something when user press the positive button
                        data!!.clearSharedPreference()
                        auth!!.signOut()
                        finish()
                        val i  = Intent(this@MainActivity,LoginActivity::class.java)
                        startActivity(i)

                    }

                    // Display a neutral button on alert dialog
                    builder.setNeutralButton("Batal"){_,_ ->
                    }

                    // Finally, make the alert dialog using builder
                    val dialog: AlertDialog = builder.create()

                    // Display the alert dialog on app interface
                    dialog.show()
                }

            }
            true
        })
        popupMenu.show()
    }

    private fun addToken(token: String){

        val user_id = auth!!.currentUser!!.uid.toString()
        val email = auth!!.currentUser!!.email
        val target = TokenModel.TokenModel(token,email.toString(),"")

        ref.child(user_id).child( "Token" ).push().setValue(target).addOnCompleteListener {
            //viewModel.addData(target)
        }

    }
    private fun getTokenFirebase(token: String){

        val getUserID: String = auth!!.getCurrentUser()?.getUid(). toString ()
        val email = auth!!.currentUser!!.email
        ref
            .child(getUserID)
            .child( "Token" )
            .orderByChild("email")
            .equalTo(email)
            .addValueEventListener( object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    //dismissLoading(swipeRefreshLayout)
                    Toast.makeText(this@MainActivity, "Database Error yaa..." ,
                        Toast. LENGTH_LONG ).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val total = dataSnapshot.childrenCount.toString()
                    if(total == "0"){
                        addToken(token)
                    }else{
                        for (snapshot in dataSnapshot. children ) {
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            val target = snapshot.getValue(TokenModel.TokenModel:: class . java )

                            if(target!!.token != token){
                                ref.child(getUserID)
                                    .child("Token")
                                    .child(target.key)
                                    .removeValue()
                                    .addOnSuccessListener {
                                    }
                                tampilToast(this@MainActivity,"token beda")
                            }

                        }
                    }

                }
            })
    }

}
