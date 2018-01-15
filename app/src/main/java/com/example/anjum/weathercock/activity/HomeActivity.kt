package com.example.anjum.weathercock.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.anjum.weathercock.R
import com.example.anjum.weathercock.R.id.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_main.*
import org.jetbrains.anko.toast

class HomeActivity : Fitoor(), View.OnClickListener {

    lateinit var progressDialogue: ProgressDialog
    lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main)

        progressDialogue = ProgressDialog(this)
        fAuth = FirebaseAuth.getInstance()
        btn_login.setOnClickListener(this)
        tv_already_reg.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v) {
            btn_login -> {
                var email: String = ed_email?.text.toString().trim()
                var password: String = ed_password?.text.toString().trim()
                if (validation()) {
                    login(email, password)
                }


            }
            tv_already_reg -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }

        }


    }

    private fun validation(): Boolean {
        var check: Boolean = true
        val email: String = ed_email.text.toString().trim()
        val password: String = ed_password.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            ed_email.error = "Please Enter Email"
            check = false
        }
        if (TextUtils.isEmpty(password)) {
            ed_password.error = "Please Enter Password"
            check = false
        }
        return check
    }

    private fun login(email: String, password: String) {
        ed_email.setText("")
        ed_password.setText("")
        progressDialogue.show()
        progressDialogue.setMessage("Please wait ....")
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {
                progressDialogue.dismiss()
                val intent = Intent(this, AddLocationActivity::class.java)
                startActivity(intent)
            } else {
                progressDialogue.dismiss()
                toast("Invalid Credentials")
            }

        }

    }

}
