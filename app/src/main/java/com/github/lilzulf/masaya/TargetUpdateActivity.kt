package com.github.lilzulf.masaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Toast
import androidx.activity.viewModels
import com.github.lilzulf.masaya.Object.MyTargetModel
import com.github.lilzulf.masaya.viewmodel.MyTargetUpdateViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_target_update.*

class TargetUpdateActivity : AppCompatActivity() {
    private var database : DatabaseReference? = null
    private var auth : FirebaseAuth? = null
    private val viewModel by viewModels<MyTargetUpdateViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_update)
        val intentData = intent.extras
        val title = intentData!!.getString("title")
        val year = intentData!!.getString("year")
        val id = intentData!!.getString("id")
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference()
        viewModel.init(this@TargetUpdateActivity);
        etTarget.setText(title)
        iv_close.setOnClickListener {
            finish()
        }
        btTarget.setOnClickListener {
            val etTitle = etTarget.text.toString()
            if(isEmpty( etTitle )){
                etTarget.requestFocus()
                etTarget.error = "Mohon isi target"
            }else{
//                Toast.makeText(this,id,Toast.LENGTH_SHORT).show()
//                Toast.makeText(this,year,Toast.LENGTH_SHORT).show()
                val newTarget = MyTargetModel(etTitle,year!!,"")
                val getUserID: String =
                    auth ?.getCurrentUser()?.getUid(). toString ()

                database !!.child(getUserID).child( "Target" )
                    .child(id!!).setValue(newTarget)
                    .addOnCompleteListener {
                        Toast.makeText( this , "Data Berhasil Disimpan" ,
                            Toast. LENGTH_SHORT ).show()
                        viewModel.updateData(newTarget)
                        finish();
                    }
            }
        }
    }

}
