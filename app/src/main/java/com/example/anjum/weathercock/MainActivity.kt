package com.example.anjum.weathercock

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var progressDialogue: ProgressDialog
    lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialogue = ProgressDialog(this)
        fAuth = FirebaseAuth.getInstance()
        btn_register.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_register) {
            registerUser()

        }
    }

    private fun registerUser() {
        progressDialogue.setMessage("Registering User...")
        progressDialogue.show()
        var email: String = ed_email?.text.toString()
        var password: String = ed_password?.text.toString()
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            ed_email.setText("")
            ed_password.setText("")
            if (it.isSuccessful) {
                progressDialogue.dismiss()
                toast("Successfull")
            } else {
                progressDialogue.dismiss()
                toast("Failure")
            }
        }

    }

}
