package com.example.anjum.weathercock.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.anjum.weathercock.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.home_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast

class SignUpActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var progressDialogue: ProgressDialog
    lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        progressDialogue = ProgressDialog(this)
        fAuth = FirebaseAuth.getInstance()
        btn_signup.setOnClickListener(this)
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }


    override fun onClick(v: View?) {
        when (v) {
            btn_signup -> {
                if (validation()) {
                    registerUser()
                }

            }
        }

    }

    private fun registerUser() {
        progressDialogue.setMessage("Registering User...")
        progressDialogue.show()
        var email: String = ed_email_signUp?.text.toString().trim()
        var password: String = ed_password_signUp?.text.toString().trim()
        var phone: String = ed_name_signUp?.text.toString().trim()

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            ed_email_signUp.setText("")
            ed_password_signUp.setText("")
            ed_name_signUp.setText("")
            if (it.isSuccessful) {
                progressDialogue.dismiss()
                toast("Successfull")
                route()
            } else {
                progressDialogue.dismiss()
                toast("Failure")
            }
        }

    }

    private fun route() {
        startActivity(Intent(SignUpActivity@ this, AddLocationActivity::class.java))
    }

    private fun validation(): Boolean {
        var check: Boolean = true
        val email: String = ed_email_signUp.text.toString().trim()
        val password: String = ed_password_signUp.text.toString().trim()
        val name: String = ed_name_signUp.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            ed_email.setError("Please Enter Email")
            check = false
        }
        if (TextUtils.isEmpty(password)) {
            ed_password.setError("Please Enter Password")
            check = false
        }
        if (TextUtils.isEmpty(name)) {
            ed_password.setError("Please Enter Name")
            check = false
        }
        return check
    }
}
