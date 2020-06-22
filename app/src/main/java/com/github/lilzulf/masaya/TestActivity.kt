package com.github.lilzulf.masaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private var token :  FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        auth = FirebaseAuth.getInstance()
        setUsername()
    }
    private fun setUsername(){
        var user = auth!!.currentUser
        tvUsername.text = user!!.displayName
        tvEmail.text = user!!.email
        tvToken.text = user!!.uid
    }
}
